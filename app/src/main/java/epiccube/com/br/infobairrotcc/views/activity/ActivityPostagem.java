package epiccube.com.br.infobairrotcc.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.views.helper.HelperActivityPostagem;

/**
 * Created by Anderson on 13/10/2016.
 */

public class ActivityPostagem extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Nova postagem");

        setContentView(R.layout.activity_postar);

        HelperActivityPostagem.init(this).cast().onClick();

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
