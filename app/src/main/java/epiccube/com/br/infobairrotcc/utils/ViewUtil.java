package epiccube.com.br.infobairrotcc.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anderson on 02/11/2016.
 */

public class ViewUtil {

    private AppCompatActivity appCompatActivity;

    public ViewUtil(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public static ViewUtil init(AppCompatActivity appCompatActivity){
        return new ViewUtil(appCompatActivity);
    }

    public void showDialog(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative){
        AlertDialog.Builder alert = new AlertDialog.Builder(appCompatActivity);
        alert.setTitle("Alerta")
                .setCancelable(false)
                .setMessage("Você está no bairro onde reside?")
                .setPositiveButton("SIM", positive)
                .setNegativeButton("NÃO", negative)
                .show();
    }

}
