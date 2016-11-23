package epiccube.com.br.infobairrotcc.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by abadari on 21/11/2016.
 */

public class AsyncHTTP {

    //TODO executa isso que vai funcionar...é um EXEMPLO
    // https://maps.googleapis.com/maps/api/geocode/json?latlng=-23.5455432,-46.6331651&key=AIzaSyDhgif6eLdQY7E1F1mxT2OqfVN19FErVJU

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }


}
