package epiccube.com.br.infobairrotcc.views.asynctask;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.mock.Mock;

/**
 * Created by abadari on 20/10/2016.
 */

public class AsynkTaskMockPostagem extends AsyncTask<Object, Integer, ArrayList<Postagem>>{

    private ArrayList<Postagem> listagemPostagem;


    @Override
    protected ArrayList<Postagem> doInBackground(Object... objects) {
        listagemPostagem = (ArrayList<Postagem>) Mock.postagens();
        return listagemPostagem;
    }

    @Override
    protected void onPostExecute(ArrayList<Postagem> postagems) {
        EventBus.getDefault().post(new Eventos.ResultadoAsyncTaskMockPostagem(postagems));
    }
}
