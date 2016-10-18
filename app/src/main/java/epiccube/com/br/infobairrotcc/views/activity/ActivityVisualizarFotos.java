package epiccube.com.br.infobairrotcc.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import epiccube.com.br.infobairrotcc.models.entities.Postagem;

/**
 * Created by abadari on 18/10/2016.
 */

public class ActivityVisualizarFotos extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        Postagem p = (Postagem) getIntent().getSerializableExtra("LISTA_FOTOS");

        Toast.makeText(this, p.getUrlFotosPostagem().size()+"", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
        }
        return super.onOptionsItemSelected(item);
    }


}
