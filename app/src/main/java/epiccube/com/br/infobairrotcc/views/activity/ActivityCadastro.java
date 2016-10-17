package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.views.helper.HelperActivityCadastro;

/**
 * Created by ivanc on 11/10/2016.
 */

public class ActivityCadastro  extends AppCompatActivity{

    private HelperActivityCadastro helperActivityCadastro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_novo_usuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        helperActivityCadastro = new HelperActivityCadastro(this);
        helperActivityCadastro.cast().onClick();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
