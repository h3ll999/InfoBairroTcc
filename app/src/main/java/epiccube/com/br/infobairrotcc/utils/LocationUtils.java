package epiccube.com.br.infobairrotcc.utils;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by abadari on 25/10/2016.
 */

public class LocationUtils {

    // TODO Obviamente rodará em asynctask...
    public String getCityName(Activity activity, Double[] coord) throws IOException {
        Geocoder gcd = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(coord[0], coord[1], 1);
        if (addresses.size() > 0){
            Log.e("getLocality", addresses.get(0).getLocality());
            Log.e("getCountryName", addresses.get(0).getCountryName());
            Log.e("getSubLocality", addresses.get(0).getSubLocality());
        }
        return "a";
    }

}
