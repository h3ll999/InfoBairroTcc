package epiccube.com.br.infobairrotcc.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by h3ll on 30/10/2016.
 */

public class Permissions {

    public static final class EXTERNAL_STORAGE{

        public static boolean temPermissao(Activity activity) {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED;
        }

        public static void solicitaPermissao(Activity activity){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);}

    }

    public static final class FINE_LOCATION{
        public static boolean temPermissao(Activity activity) {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED;
        }

        public static void solicitaPermissao(Activity activity){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);}
    }


    public static final class COARSE_LOCATION{
        public static boolean temPermissao(Activity activity) {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED;
        }

        public static void solicitaPermissao(Activity activity){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);}
    }


    public void a (Activity activity){
        if(Permissions.EXTERNAL_STORAGE.temPermissao(activity)){
            Permissions.EXTERNAL_STORAGE.solicitaPermissao(activity);
        }
    }

}
