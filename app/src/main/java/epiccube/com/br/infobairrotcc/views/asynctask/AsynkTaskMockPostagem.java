package epiccube.com.br.infobairrotcc.views.asynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;

import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.mock.Mock;

/**
 * Created by abadari on 20/10/2016.
 */

public class AsynkTaskMockPostagem extends AsyncTask<ProgressBar, Integer, ArrayList<Postagem>>{

    private ArrayList<Postagem> listagemPostagem;
    private ProgressBar p;

    @Override
    protected ArrayList<Postagem> doInBackground(ProgressBar... objects) {
        this.p = objects[0];
        listagemPostagem = (ArrayList<Postagem>) Mock.postagens();
        return listagemPostagem;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        p.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Postagem> postagems) {
        EventBus.getDefault().post(new Eventos.ResultadoAsyncTaskMockPostagem(postagems));
    }

}
