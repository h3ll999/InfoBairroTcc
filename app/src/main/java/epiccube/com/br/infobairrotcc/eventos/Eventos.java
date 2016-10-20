package epiccube.com.br.infobairrotcc.eventos;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Anderson on 13/10/2016.
 */

public class Eventos {

    public static class FechaDialogoPostagem{}

    public static class SelecionaImagemSelecionada{

        private Uri imagemSelecionada;

        public SelecionaImagemSelecionada(Uri imagemSelecionada){
            this.imagemSelecionada = imagemSelecionada;
        }

        public Uri getImagemSelecionada() {
            return imagemSelecionada;
        }
    }

    public static class PostagemMultiplasImagens{
        private ArrayList<Uri> fotos = new ArrayList<>();

        public PostagemMultiplasImagens(ArrayList<Uri> fotos) {
            this.fotos = fotos;
        }

        public ArrayList<Uri> getFotos() {
            return fotos;
        }

        public void setFotos(ArrayList<Uri> fotos) {
            this.fotos = fotos;
        }
    }

    public static class AparecerImagem{}





}
