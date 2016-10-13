package epiccube.com.br.infobairrotcc.views.helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import epiccube.com.br.infobairrotcc.views.activity.ActivityCadastro;
import epiccube.com.br.infobairrotcc.views.activity.ActivityMenuInicial;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.entities.Usuario;

/**
 * Created by ivanc on 11/10/2016.
 */

public class HelperTelaLogin {

    private AppCompatActivity context;

    private EditText login_edt_email;
    private EditText login_edt_senha;
    private Button login_btn_login;
    private Button login_btn_cadastrar;

    private Usuario usuario;

    String email;
    String senha;

    public HelperTelaLogin(AppCompatActivity context){
        this.context = context;
    }

    public static HelperTelaLogin init(AppCompatActivity context){
        return new HelperTelaLogin(context);
    }

    public HelperTelaLogin cast(){

        login_edt_email = (EditText) context.findViewById(R.id.login_edt_email);
        login_edt_senha = (EditText) context.findViewById(R.id.login_edt_senha);
        login_btn_login = (Button) context.findViewById(R.id.login_btn_login);
        login_btn_cadastrar = (Button) context.findViewById(R.id.login_btn_cadastrar);

        return this;

    }

    public HelperTelaLogin onClick(){
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = login_edt_email.getText().toString().trim();
                senha = login_edt_senha.getText().toString().trim();

                Intent i = new Intent(context, ActivityMenuInicial.class);
                context.startActivity(i);

                //Firebase

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
}
