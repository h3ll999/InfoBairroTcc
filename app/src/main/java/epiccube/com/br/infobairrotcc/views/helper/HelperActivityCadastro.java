package epiccube.com.br.infobairrotcc.views.helper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.mock.Mock;
import epiccube.com.br.infobairrotcc.models.singleton.SingletonUsuario;
import epiccube.com.br.infobairrotcc.validator.Validar;
import epiccube.com.br.infobairrotcc.views.activity.ActivityMenuInicial;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ivanc on 13/10/2016.
 */

public class HelperActivityCadastro {

    private AppCompatActivity context;
    private ImageView cadastro_img_perfil;
    private EditText cadastro_edt_nome;
    private EditText cadastro_edt_email;
    private EditText cadastro_edt_senha;
    private Button cadastro_btn_cadastrar;

    private ProgressDialog progressDialog;

    private String nome;
    private String email;
    private String senha;
    private Usuario usuario;
    private Uri caminhoImagemSelecionada;

    private FirebaseAuth autenticador;
    private FirebaseUser user;
    private DatabaseReference database;

    public HelperActivityCadastro(AppCompatActivity context){
        this.context=context;
        EventBus.getDefault().register(this); // REGISTRA NA CLASSE O OUVINTE
        this.context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static HelperActivityCadastro init(AppCompatActivity context){
        return new HelperActivityCadastro(context);
    }

    public HelperActivityCadastro cast(){

        cadastro_img_perfil = (ImageView) context.findViewById(R.id.cadastro_img_perfil);
        cadastro_edt_nome = (EditText) context.findViewById(R.id.cadastro_edt_nome);
        cadastro_edt_email = (EditText) context.findViewById(R.id.cadastro_edt_email);
        cadastro_edt_senha = (EditText) context.findViewById(R.id.cadastro_edt_senha);
        cadastro_btn_cadastrar = (Button) context.findViewById(R.id.cadastro_btn_cadastrar);
        cadastro_btn_cadastrar.getBackground().setColorFilter(Color.parseColor("#ff4e43"), PorterDuff.Mode.SRC_ATOP); //TODO ARUMAR ESSA PORCARIA

        //autenticador = FirebaseAuth.getInstance();

        return this;
    }

    public HelperActivityCadastro onClick(){

        cadastro_img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore
                .Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intentGaleria, 1);

                // TODO pegar imagem e subir no firebase // SÓMENTE POR ÚLTIMO

            }
        });

        cadastro_btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastro_btn_cadastrar.setEnabled(false);

                progressDialog = ProgressDialog.show(context,"Cadastrando","Aguarde...", true, false);

                nome = cadastro_edt_nome.getText().toString().trim();
                email = cadastro_edt_email.getText().toString().trim();
                senha = cadastro_edt_senha.getText().toString().trim();

                if(Validar.CADASTRO(nome,email,senha)){

                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    firebase();
                    /*SingletonUsuario.getInstancia().setUsuario(Mock.usuario());

                    Toast.makeText(context, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, ActivityMenuInicial.class);
                    context.startActivity(intent);
                    context.finish();*/

                }else{
                    cadastro_btn_cadastrar.setEnabled(true);
                    //progressDialog.dismiss();
                    Toast.makeText(context, "Erro ao cadastrar campos vazios ou senha curta. " +
                            "A senha precisa ter 6 ou mais caracteres", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return this;
    }

    private void firebase() {
        autenticador = FirebaseAuth.getInstance();
        autenticador.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user = authResult.getUser();
                        usuario.setUid(user.getUid());
                        finalizaInsercaoDados();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void finalizaInsercaoDados(){
        database = FirebaseDatabase.getInstance().getReference();

        database.child(Constantes.USUARIO).child(user.getUid()).child(Constantes.PERFIL).setValue(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();

                        //Cria usuário falso e o salva numa variavel estática
                        SingletonUsuario.getInstancia().setUsuario(usuario);

                        Toast.makeText(context, usuario.getNome()+", seu cadastrado foi realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, ActivityMenuInicial.class);
                        context.startActivity(intent);
                        context.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    // MÉTODO PARA OUVIR A CHAMADA DO POST...RECEBE O PARÂMETRO DO EventBus.getDefault.post();
    @Subscribe
    public void abrirImagemSelecionada(Eventos.SelecionaImagemSelecionada imagemSelecionada){
        caminhoImagemSelecionada = imagemSelecionada.getImagemSelecionada();
        Glide.get(context).clearMemory();
        Glide.with(context).load(caminhoImagemSelecionada)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(cadastro_img_perfil);

    }
}
