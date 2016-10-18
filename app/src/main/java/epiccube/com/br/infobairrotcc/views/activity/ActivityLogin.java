package epiccube.com.br.infobairrotcc.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.views.helper.HelperActivityLogin;

/**
 * Created by ivanc on 11/10/2016.
 */

public class ActivityLogin extends AppCompatActivity {

    //TODO bibliotecas bem dahoras
    // https://github.com/KeepSafe/TapTargetView
    // https://github.com/sjwall/MaterialTapTargetPrompt
    // https://github.com/truizlop/FABRevealLayout

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        HelperActivityLogin.init(this).cast().onClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
