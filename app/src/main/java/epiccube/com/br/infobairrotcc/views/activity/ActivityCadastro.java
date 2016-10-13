package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.views.helper.HelperTelaCadastro;

/**
 * Created by ivanc on 11/10/2016.
 */

public class ActivityCadastro  extends AppCompatActivity{

    private HelperTelaCadastro helperTelaCadastro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_novo_usuario);

        helperTelaCadastro = new HelperTelaCadastro(this);
        helperTelaCadastro.cast().onClick();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {
                Uri perfilSelecionado = data.getData();
                helperTelaCadastro.abrirImagemSelecionada(perfilSelecionado);
            }
        }
    }
}
