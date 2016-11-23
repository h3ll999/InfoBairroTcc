package epiccube.com.br.infobairrotcc.views.asynctask;

import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import epiccube.com.br.infobairrotcc.utils.LocationUtils;
import epiccube.com.br.infobairrotcc.views.asynctask.callback.AsyncTaskLocationCallBack;

/**
 * Created by abadari on 22/11/2016.
 */

public class AsyncTaskLocation extends AsyncTask<Context, Integer, List<Address>> {

    private Context activity;
    private Double[] coord;
    private LocationUtils locationUtils;
    private AsyncTaskLocationCallBack callBack;

    public AsyncTaskLocation(Context activity, Double[] coord) {
        this.activity = activity;
        this.coord = coord;
        Log.e("AsyncTaskLocation", "Construtor");
        //this.callBack = callBack;
    }

    @Override
    protected List<Address> doInBackground(Context... contexts) {

        Log.e("AsyncTaskLocation", "doInBackground");

        locationUtils = new LocationUtils(activity, coord);

        try {

            Log.e("AsyncTaskLocation", "return");
            return locationUtils.getLocais();


        } catch (IOException e) {
            Log.e("AsyncTaskLocation", "return null");
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        Log.e("AsyncTaskLocation", "onPostExecute");
        locationUtils.finalizar(addresses);
        //callBack.result();
    }
}
