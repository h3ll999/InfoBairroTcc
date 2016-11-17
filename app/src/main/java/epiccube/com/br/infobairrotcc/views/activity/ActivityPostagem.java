package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.views.helper.HelperActivityPostagem;

/**
 * Created by Anderson on 13/10/2016.
 */

public class ActivityPostagem extends AppCompatActivity {

    private HelperActivityPostagem helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        //getSupportActionBar().setCustomView();

        setContentView(R.layout.activity_postagem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_postar_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Nova postagem");
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);

        helper = new HelperActivityPostagem(this);
        helper.cast().onClick();

        //TODO MAIS TEM QUE FICAR CINZA MEIO ESCURO...
        Toast.makeText(this,"TODO: O '+' tem que ficar cinza escuro...", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_CANCELED) {// se não foi cancelado, entra aqui...
            if (requestCode == 1) {

                ArrayList<Uri> fotos = new ArrayList<>();

                if(data.getClipData()==null){ //ÚNICA FOTO
                    fotos.add(data.getData());
                } else { //VÁRIAS FOTO
                    ClipData clipData = data.getClipData();

                    int qtdFotos = clipData.getItemCount();

                    for (int i = 0; i<qtdFotos;i++){
                        ClipData.Item item = clipData.getItemAt(i);
                        fotos.add(item.getUri());
                        Log.e("FOTO PEGOU", item.getUri().getPath());
                    }
                }

                // Passa o array de fotos para o "além" e espera alguém ouvir.
                // Quem vai ouvir é a classe HelperActivityPostagem...
                EventBus.getDefault().post(new Eventos.PostagemMultiplasImagens(fotos));
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.postagem, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new Eventos.Unregister());
    }

    @Override
    public void onBackPressed() {
        finish();
        //overridePendingTransition(R.anim.anim_down_primeiro, R.anim.anim_down_segundo);
    }


}
