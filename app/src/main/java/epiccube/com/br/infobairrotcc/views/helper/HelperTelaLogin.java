package epiccube.com.br.infobairrotcc.views.helper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import epiccube.com.br.infobairrotcc.views.activity.ActivityCadastro;
import epiccube.com.br.infobairrotcc.views.activity.ActivityMenuInicial;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;

/**
 * Created by ivanc on 11/10/2016.
 */

public class HelperTelaLogin {

    private AppCompatActivity context;

    private EditText login_edt_email;
    private EditText login_edt_senha;
    private Button login_btn_login;
    private TextView login_btn_cadastrar;

    private Usuario usuario;

    private String email;
    private String senha;

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
        login_btn_login.getBackground().setColorFilter(Color.parseColor("#ff4e43"), PorterDuff.Mode.SRC_ATOP); //TODO ARUMAR ESSA PORCARIA
        login_btn_cadastrar = (TextView) context.findViewById(R.id.login_btn_cadastrar);

        return this;

    }

    public HelperTelaLogin onClick(){
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = login_edt_email.getText().toString().trim();
                senha = login_edt_senha.getText().toString().trim();

                // Firebase onSuccess
                Intent i = new Intent(context, ActivityMenuInicial.class);
                context.startActivity(i);

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
