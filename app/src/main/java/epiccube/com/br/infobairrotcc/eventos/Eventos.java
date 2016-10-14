package epiccube.com.br.infobairrotcc.eventos;

import android.net.Uri;

/**
 * Created by Anderson on 13/10/2016.
 */

public class Eventos {



    public static class SelecionaImagemSelecionada{

        private Uri imagemSelecionada;

        public SelecionaImagemSelecionada(Uri imagemSelecionada){
            this.imagemSelecionada = imagemSelecionada;
        }

        public Uri getImagemSelecionada() {
            return imagemSelecionada;
        }
    }



}
