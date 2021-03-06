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

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;

/**
 * Created by abadari on 25/10/2016.
 */

public class MyGPS implements LocationListener {

    private Context context;
    private LocationManager mLocationManager;
    private Double[] coord;

    //TODO Solicitar a Permissão ANTES de chamar essa classe...
    // http://stackoverflow.com/questions/10524381/gps-android-get-positioning-only-once
    public MyGPS(Context context) {
        this.context = context;

    }

    public void init() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            // caso faz 2 minutos que pegou o local, pega o último local...
            coord = new Double[2];
            coord[0] = location.getLatitude();
            coord[1] = location.getLongitude();
            Log.e("MyGps--getLastKnownLoca","Lat: "+coord[0]+" | Long "+coord[1]);
            mLocationManager.removeUpdates(this);

            EventBus.getDefault().post(new Eventos.PegaCoordenada(coord));
        } else {
            // mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    public Double[] getCoord() {
        return coord;
    }

    public void setCoord(Double[] coord) {
        this.coord = coord;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            coord = new Double[2];
            coord[0] = location.getLatitude();
            coord[1] = location.getLongitude();

            Log.e("MyGps--onLocationChange","Lat: "+coord[0]+" | Long "+coord[1]);

            try{
                mLocationManager.removeUpdates(this);
            }catch (SecurityException e){
                Log.e("MyGps--getLastKnownLoca",e.getMessage());
            }


            // TODO .... cagada...?!?!?!?
            EventBus.getDefault().post(new Eventos.PegaCoordenada(coord));

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
