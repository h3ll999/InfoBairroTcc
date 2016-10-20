package epiccube.com.br.infobairrotcc.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.views.helper.HelperVisualizaPostagem;

/**
 * Created by Anderson on 13/10/2016.
 */

public class ActivityVisualizaPostagem extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_postagem);

        Postagem postagem = (Postagem) getIntent().getSerializableExtra(Constantes.INTENT_POSTAGEM);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(postagem.getTitulo());

        HelperVisualizaPostagem visualizaPostagem = new HelperVisualizaPostagem(this, postagem);
        visualizaPostagem.cast().onClick().configure();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
        }

        return super.onOptionsItemSelected(item);
    }
}
