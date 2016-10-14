package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
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
                //DÁ UM "GRITO" E JOGA O OBJETO PARA O "ALÉM" E ESPERA QUE ALGUÉM OUÇA...QUEM VAI OUVIR É QUEM TIVER O @Subscribe como annotation e quem receber o parâmetro do mesmo tipo
                EventBus.getDefault().post(new Eventos.SelecionaImagemSelecionada(perfilSelecionado));
            }
        }
    }
}
