package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.views.helper.HelperActivityPostagem;

/**
 * Created by Anderson on 13/10/2016.
 */

public class ActivityPostagem extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Nova postagem");

        setContentView(R.layout.activity_postagem);

        HelperActivityPostagem.init(this).cast().onClick().configure();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("FOTO", "AAA");
        if(resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {
                if (Intent.ACTION_SEND_MULTIPLE.equals(data.getAction())&& data.hasExtra(Intent.EXTRA_STREAM)) { //TODO BUGADO
                    ArrayList<Parcelable> list = data.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                    if( list != null ) {
                        for (Parcelable parcel : list) {
                            Uri uri = (Uri) parcel;
                            Log.e("FOTO", uri.getPath());
                        }
                    }
                }
                /*Uri perfilSelecionado = data.getData();
                EventBus.getDefault().post(new Eventos.SelecionaImagemSelecionada(perfilSelecionado));*/
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_down_primeiro, R.anim.anim_down_segundo);
    }


}
