package epiccube.com.br.infobairrotcc.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.views.adapter.AdapterFotos;

/**
 * Created by abadari on 18/10/2016.
 */

public class ActivityVisualizarFotos extends AppCompatActivity{

    private Postagem p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_fotos);



        // pega a postagem que foi passada
        p = (Postagem) getIntent().getSerializableExtra("LISTA_FOTOS");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setElevation(0);
        //getSupportActionBar().setTitle(p.getTitulo());

        //Toast.makeText(this, p.getUrlFotosPostagem().size()+"", Toast.LENGTH_SHORT).show();

        setViewPager();

    }

    void setViewPager(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_visualizar_fotos_view_pager);
        viewPager.setAdapter(new AdapterFotos(p.getUrlFotosPostagem(), this));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
        }
        return super.onOptionsItemSelected(item);
    }


}
