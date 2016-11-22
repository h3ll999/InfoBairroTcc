package epiccube.com.br.infobairrotcc.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import epiccube.com.br.infobairrotcc.eventos.Eventos;

/**
 * Created by abadari on 25/10/2016.
 */

public class LocationUtils {

    private Activity activity;
    private Double[] coord;
    private String [] locais;

    private int contador;

    public LocationUtils(Activity activity, Double[] coord){
        this.activity = activity;
        this.coord = coord;
        contador = 0;
    }

    public void getLocais() throws IOException {
        Log.e("LocationUtils", "BATEU");
        Geocoder gcd = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(coord[0], coord[1], 1); //TODO verificar se esse troço é ASÍNCRONO MESMO

        //TODO daqui pra baixo é tudo NOUTRO LUGAR...
        if (addresses.size() > 0){
            locais = new String[3]; // cidade - bairro
            locais[0] = addresses.get(0).getAdminArea(); // estado
            locais[1] = addresses.get(0).getLocality(); // cidade
            locais[2] = addresses.get(0).getSubLocality(); // bairro

            //Log.e("LocationUtils-getLocais",locais[0]+" | "+locais[1]+" | "+locais[2]);

            //verificaSeEstaNulo(locais);

            EventBus.getDefault().post(new Eventos.PegaEndereco(locais));

        } else {
            // TODO CRY
        }
    }

    public void aaaaa(){

    }

    /*private void verificaSeEstaNulo(String [] locais) throws IOException {

        if(locais[0] == null ||
                locais[1] == null ||
                    locais[2] == null){

            if(contador < 5){
                //Se os dados não chegarem, então chama o método até o dado chegar...
                contador++;
                getLocais();
            }
        }
    }*/

    public boolean servicoDisponivel(AppCompatActivity activity){

        if(locais[0] == null ||
                locais[1] == null ||
                    locais[2] == null){

            ViewUtil.init(activity).showDialog(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }, "O serviço de localização, mantido pela Google, " +
                    "está indisponível no momento. Tente novamente em alguns instantes." +
                    "Entretanto, você poderá visualizar as notícias do seu bairro de origem.");

            return false;
        } else {return true;}
    }
}
