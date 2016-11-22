package epiccube.com.br.infobairrotcc.views.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import epiccube.com.br.infobairrotcc.utils.MyGPS;
import epiccube.com.br.infobairrotcc.views.asynctask.callback.AsyncTaskGPSCallBack;

/**
 * Created by abadari on 22/11/2016.
 */

public class AsyncTaskGPS extends AsyncTask<String, Integer, Double[]> {

    private Context context;
    private AsyncTaskGPSCallBack callBack;

    public AsyncTaskGPS(Context context, AsyncTaskGPSCallBack callBack) {
        this.context = context;
    }

    @Override
    protected Double[] doInBackground(String... strings) {

        MyGPS myGPS = new MyGPS(context);
        myGPS.init();
        // get valores

        return myGPS.getCoord();
    }

    @Override
    protected void onPostExecute(Double[] doubles) {

        callBack.result(doubles);

    }
}
