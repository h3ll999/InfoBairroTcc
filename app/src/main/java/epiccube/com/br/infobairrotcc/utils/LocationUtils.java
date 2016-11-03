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

    public String[] getLocais(Activity activity, Double[] coord) throws IOException {
        Geocoder gcd = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(coord[0], coord[1], 1);
        if (addresses.size() > 0){
            String [] locais = new String[3]; // cidade - bairro
            locais[0] = addresses.get(0).getAdminArea(); // estado
            locais[1] = addresses.get(0).getLocality(); // cidade
            locais[2] = addresses.get(0).getSubLocality(); // bairro
            return locais;
        } else {
            return null; //TODO ESSA COISA
        }
    }
}
