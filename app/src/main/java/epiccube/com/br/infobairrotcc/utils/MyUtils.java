package epiccube.com.br.infobairrotcc.utils;

import android.app.Activity;

import java.text.Normalizer;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;

import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.EVENTOS;
import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.NOTICIAS;
import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.POSTAGENS_EVENTOS;
import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.POSTAGENS_NOTICIAS;
import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.POSTAGENS_SERVICOS;
import static epiccube.com.br.infobairrotcc.models.contantes.Constantes.SERVICOS;

/**
 * Created by abadari on 18/10/2016.
 */

public class MyUtils {

    public static String verificaFormatacaoPostagem(String conteudo){
        String conteudoFormatado;
        if (conteudo.length()>=140){
            conteudoFormatado = conteudo.substring(0,127)+"...[Ver mais]";
            return conteudoFormatado;
        } else return conteudo;
    }

    public static String formatCategoria(String categoria){

        switch (categoria){
            case NOTICIAS: categoria = POSTAGENS_NOTICIAS;
                break;
            case EVENTOS: categoria = POSTAGENS_EVENTOS;
                break;
            case SERVICOS: categoria = POSTAGENS_SERVICOS;
                break;
        }

        return categoria;
    }

    private static String removeAcentosEspacosP(String input){
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s","").toUpperCase();
    }

    public static String removeAcentosEspacos(String input){
        return removeAcentosEspacosP(input);
    }



}
