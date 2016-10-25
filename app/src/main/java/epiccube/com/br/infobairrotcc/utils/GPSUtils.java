package epiccube.com.br.infobairrotcc.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by abadari on 25/10/2016.
 */

public class GPSUtils implements LocationListener {

    private Context context;
    private LocationManager mLocationManager;

    // http://stackoverflow.com/questions/10524381/gps-android-get-positioning-only-once
    public GPSUtils(Context context) {
        this.context = context;
    }

    public void init() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //TODO Solicitar a Permissão ANTES de chamar esse troço
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            //TODO caso faz 2 minutos que pegou o local, pega o último local.
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            //TODO location é o objeto com as coordenadas...
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());

            //TODO Solicitar a Permissão ANTES de chamar esse troço
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
