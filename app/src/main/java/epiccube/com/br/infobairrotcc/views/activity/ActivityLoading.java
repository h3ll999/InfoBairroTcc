package epiccube.com.br.infobairrotcc.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;

/**
 * Created by abadari on 16/11/2016.
 */

public class ActivityLoading extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_loading_translucent);

        EventBus.getDefault().register(this);

    }


    @Subscribe
    public void onEventFimLoading(Eventos.FimLoading fimLoading){
        finish();
    }

    @Override
    public void onBackPressed() {
        // não faz nada...
    }
}
