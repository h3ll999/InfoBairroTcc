package epiccube.com.br.infobairrotcc.views.helper;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.entities.Usuario;
import epiccube.com.br.infobairrotcc.validator.ValidatorCadUsuario;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ivanc on 13/10/2016.
 */

public class HelperTelaCadastro {

    private AppCompatActivity context;
    private ImageView cadastro_img_perfil;
    private EditText cadastro_edt_nome;
    private EditText cadastro_edt_email;
    private EditText cadastro_edt_senha;
    private Button cadastro_btn_cadastrar;
    private String nome;
    private String email;
    private String senha;
    private boolean validatorCadUsuario;
    boolean testeCadastro;
    private Usuario usuario;
    private Uri caminhoImagemSelecionada;

    public HelperTelaCadastro(AppCompatActivity context){
        this.context=context;
    }

    public static HelperTelaCadastro init(AppCompatActivity context){
        return new HelperTelaCadastro(context);
    }

    public HelperTelaCadastro cast(){

        cadastro_img_perfil = (ImageView) context.findViewById(R.id.cadastro_img_perfil);
        cadastro_edt_nome = (EditText) context.findViewById(R.id.cadastro_edt_nome);
        cadastro_edt_email = (EditText) context.findViewById(R.id.cadastro_edt_email);
        cadastro_edt_senha = (EditText) context.findViewById(R.id.cadastro_edt_senha);
        cadastro_btn_cadastrar = (Button) context.findViewById(R.id.cadastro_btn_cadastrar);

        return this;
    }

    public HelperTelaCadastro onClick(){

        cadastro_img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore
                .Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intentGaleria, 1);

                // TODO pegar imagem e subir no firebase

            }
        });

        cadastro_btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = cadastro_edt_nome.getText().toString().trim();
                email = cadastro_edt_email.getText().toString().trim();
                senha = cadastro_edt_senha.getText().toString().trim();

                testeCadastro = validatorCadUsuario = new ValidatorCadUsuario(nome,email,senha).validaCadastro();

                if(testeCadastro == true){

                    Toast.makeText(context, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // com o cadastro validado enviado os valores para entidade usuario
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    //TODO - cadastrar usuario no firebase

                }else{
                    Toast.makeText(context, "Erro ao cadastrar campos vazios ou senha curta " +
                            "a senha precisa ter 6 ou mais caracteres", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return this;
    }

    public void abrirImagemSelecionada(Uri imagemSelecionada){

        caminhoImagemSelecionada = imagemSelecionada;
        Glide.get(context).clearMemory();
        Glide.with(context).load(caminhoImagemSelecionada)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(cadastro_img_perfil);

    }
}
