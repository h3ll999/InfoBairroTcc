package epiccube.com.br.infobairrotcc.views.helper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blitzk01.androidbeginnersutillibrary.main.ABU;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.UUID;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.utils.LocationUtils;
import epiccube.com.br.infobairrotcc.utils.MyGPS;
import epiccube.com.br.infobairrotcc.utils.Permissions;
import epiccube.com.br.infobairrotcc.utils.ViewUtil;
import epiccube.com.br.infobairrotcc.validator.Validar;
import epiccube.com.br.infobairrotcc.views.activity.ActivityMenuInicial;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.REPOSITORIO_FOTOS;

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
    private Uri caminhoImagemSelecionada; boolean selecinouFoto;

    private FirebaseAuth autenticador;
    private FirebaseUser user;
    private DatabaseReference database;

    private Double [] coord;
    private String[] locais;

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
        cadastro_btn_cadastrar.getBackground().setColorFilter(Color.parseColor("#ff4e43"), PorterDuff.Mode.SRC_ATOP);

        Glide.with(context).load(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .crossFade()
                .into(cadastro_img_perfil);

        //autenticador = FirebaseAuth.getInstance();
        selecinouFoto = false;

        return this;
    }

    public HelperActivityCadastro onClick(){

        cadastro_img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Permissions.EXTERNAL_STORAGE.temPermissao(context)){
                    Permissions.EXTERNAL_STORAGE.solicitaPermissao(context);
                } else {
                    Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore
                            .Images.Media.EXTERNAL_CONTENT_URI);
                    context.startActivityForResult(intentGaleria, 1);
                }
            }
        });

        cadastro_btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = ProgressDialog.show(context,"Cadastrando","Aguarde...", true, false);

                cadastro_btn_cadastrar.setEnabled(false);

                nome = cadastro_edt_nome.getText().toString().trim();
                email = cadastro_edt_email.getText().toString().trim();
                senha = cadastro_edt_senha.getText().toString().trim();

                if(Validar.CADASTRO(nome,email,senha)){
                    cadastro_btn_cadastrar.setEnabled(false);
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    //usuario.setSenha(senha);

                    //firebase();
                    location();
                    /*UsuarioLogado.getInstancia().setUsuario(Mock.usuario());

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
        progressDialog = ProgressDialog.show(context,"Finalizando", "Aguarde...", true, false);
        autenticador = FirebaseAuth.getInstance();
        autenticador.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user = authResult.getUser();
                        usuario.setUid(user.getUid());
                        if(selecinouFoto){// insere foto
                            uploadFotoPerfil();
                        } else {
                            finalizaInsercaoDados();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadFotoPerfil(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(REPOSITORIO_FOTOS);
        StorageReference imagem = storageRef.child("PERFIL").child(UUID.randomUUID()+".jpg");
        UploadTask uploadTask = imagem.putFile(caminhoImagemSelecionada);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                usuario.setPerfilUrl(taskSnapshot.getDownloadUrl().toString());
                finalizaInsercaoDados();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Erro no envio da foto do perfil. Tente mais tarde", Toast.LENGTH_SHORT).show();
                        finalizaInsercaoDados();
                    }
                });
    }

    void finalizaInsercaoDados(){
        database = FirebaseDatabase.getInstance().getReference();

        database.child(Constantes.USUARIO).child(user.getUid()).child(Constantes.PERFIL).setValue(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UsuarioLogado.getInstancia().setUsuario(usuario);
                        prosseguir();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    void location(){
        progressDialog.setTitle("Localização");
        MyGPS myGPS = new MyGPS(context);
        myGPS.init();
    }

    @Subscribe
    public void pegaCoordenada(Eventos.PegaCoordenada coordenada){
        coord = coordenada.getCood();
        usuario.setLatitudeLongitude(coord);
        Log.e("pegaCoordenada","HelperActivityCadastro");
        locais();
    }


    void locais(){

        try {

            LocationUtils l = new LocationUtils(context, coord);

            // TODO CONSERTAR ESSE NEGÓCIO...
            //locais = l.getLocais();

            progressDialog.dismiss();

            // TODO verificar melhor essa coisa nesse caso...
            /*if (!l.servicoDisponivel(context)){
                return;
                // interrompe o fluxo
            }*/

            usuario.setEstadoAtualId(locais[0]);
            usuario.setCidadeAtualId(locais[1]);
            usuario.setBairroAtualId(locais[2]);

            if(locais[0]==null&&locais[1]==null&&locais[2]==null){
                throw new Exception("Erro: ");
            }

            pergunta();

        } catch (Exception e) {
            Toast.makeText(context, "Erro(remover esse toast): "+e.getMessage(), Toast.LENGTH_SHORT).show();
            locais();// caso dá erro, chama de novo...
        }
    }

    void pergunta(){
        // pergunta se é o bairro residencial
        ViewUtil.init(context).showDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // se sim, então prosseguir (salvar o local no banco...)
                usuario.setPermissaoPostagem(true);
                usuario.setEstadoOrigemId(locais[0]);
                usuario.setCidadeOrigemId(locais[1]);
                usuario.setBairroOrigemId(locais[2]);
                firebase();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // se não, prosseguir e não pode postar -- só visualizar...pergunta(); até ter o bairro de origem no banco...
                usuario.setPermissaoPostagem(false);
                firebase();
            }
        });

    }

    void prosseguir(){

        progressDialog.dismiss();

        Intent intent = new Intent(context, ActivityMenuInicial.class);
        context.startActivity(intent);
        context.finish();
    }



    // MÉTODO PARA OUVIR A CHAMADA DO POST...RECEBE O PARÂMETRO DO EventBus.getDefault.post();
    @Subscribe
    public void abrirImagemSelecionada(Eventos.SelecionaImagemSelecionada imagemSelecionada){
        selecinouFoto = true;
        caminhoImagemSelecionada = imagemSelecionada.getImagemSelecionada();
        Glide.get(context).clearMemory();
        Glide.with(context).load(caminhoImagemSelecionada)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(cadastro_img_perfil);

    }

    @Subscribe
    public void onEventAbrirGaleria(Eventos.AbrirGaleria galeria){
        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore
                .Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intentGaleria, 1);
    }

    @Subscribe
    public void onEventUnregister(Eventos.Unregister unregister){
        EventBus.getDefault().unregister(this);
    }

}
