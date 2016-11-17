package epiccube.com.br.infobairrotcc.views.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.mock.Mock;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.validator.Validar;
import epiccube.com.br.infobairrotcc.views.activity.ActivityCadastro;
import epiccube.com.br.infobairrotcc.views.activity.ActivityLoading;
import epiccube.com.br.infobairrotcc.views.activity.ActivityMenuInicial;

/**
 * Created by ivanc on 11/10/2016.
 */

public class HelperActivityLogin {

    private AppCompatActivity context;

    private EditText login_edt_email;
    private EditText login_edt_senha;
    private Button login_btn_login;
    private TextView login_btn_cadastrar;

    private Usuario usuario;

    private String email;
    private String senha;

    private DatabaseReference database;
    private FirebaseAuth autenticador;
    private FirebaseAuth.AuthStateListener autenticadorOuvinte;
    private FirebaseUser user;

    private ProgressDialog progressDialog;

    public HelperActivityLogin(AppCompatActivity context){
        this.context = context;
        database = FirebaseDatabase.getInstance().getReference();
        autenticador = FirebaseAuth.getInstance();
        autenticadorOuvinte = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser u = autenticador.getCurrentUser();
                if (u!=null){
                    Log.e("HelperActivityLogin","Não serve pra nada");
                }
            }
        };
        autenticador.addAuthStateListener(autenticadorOuvinte);
    }

    public static HelperActivityLogin init(AppCompatActivity context){
        return new HelperActivityLogin(context);
    }

    public HelperActivityLogin cast(){

        login_edt_email = (EditText) context.findViewById(R.id.login_edt_email);
        login_edt_senha = (EditText) context.findViewById(R.id.login_edt_senha);
        login_btn_login = (Button) context.findViewById(R.id.login_btn_login);
        login_btn_login.getBackground().setColorFilter(Color.parseColor("#ff4e43"), PorterDuff.Mode.SRC_ATOP);
        login_btn_cadastrar = (TextView) context.findViewById(R.id.login_btn_cadastrar);

        //autenticador = FirebaseAuth.getInstance();// liga o autenticador

        return this;

    }

    public HelperActivityLogin onClick(){
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = login_edt_email.getText().toString().trim();
                senha = login_edt_senha.getText().toString().trim();

                if(Validar.LOGIN(email,senha)){
                    firebase();
                } else {
                    Toast.makeText(context, "Insira os campos corretamente", Toast.LENGTH_SHORT).show();
                }

                /*UsuarioLogado.getInstancia().setUsuario(Mock.usuario());
                //Troca de tela
                Intent i = new Intent(context, ActivityMenuInicial.class);
                context.startActivity(i);*/

            }
        });

        login_btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ActivityCadastro.class);
                context.startActivity(i);


            }
        });
        return this;
    }

    private void firebase(){
        // TODO chama a activity
        Intent intent = new Intent(context, ActivityLoading.class);
        context.startActivity(intent);

        //progressDialog = ProgressDialog.show(context,"Entrando", "Aguarde...",true,false);
        //autentica
        autenticador.signInWithEmailAndPassword(email,senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user = authResult.getUser();
                        finalizaRequisicaoDados();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new Eventos.FimLoading());
                        //progressDialog.dismiss();
                    }
                });

    }

    private void finalizaRequisicaoDados(){

        database.child(Constantes.USUARIO).child(user.getUid()).child(Constantes.PERFIL)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //progressDialog.dismiss();

                /*HashMap<String, String> a = (HashMap<String, String>) dataSnapshot.getValue();

                //Pega os dados que chegaram...
                Usuario u = new Usuario();
                u.setNome(a.get("nome"));
                u.setEmail(a.get("email"));
                u.setPerfilUrl(a.get("perfilUrl"));
                u.setPermissaoPostagem("permissaoPostagem");*/

                //Pega TODOS OS dados que chegaram...
                Usuario u = dataSnapshot.getValue(Usuario.class);

                //Salva o usuário numa variavel estática
                UsuarioLogado.getInstancia().setUsuario(u);

                //Troca de tela
                Intent i = new Intent(context, ActivityMenuInicial.class);
                context.startActivity(i);
                EventBus.getDefault().post(new Eventos.FimLoading());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                context.finish();
            }
        });
    }

    public void autoLogin(Activity activity){
        Intent intent = new Intent(context, ActivityLoading.class);
        context.startActivity(intent);
        //progressDialog = ProgressDialog.show(activity, "Entrando", "Aguarde...", true,false);
        database = FirebaseDatabase.getInstance().getReference();;
        autenticador = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        finalizaRequisicaoDados();
    }

}
