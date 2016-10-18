package epiccube.com.br.infobairrotcc.utils;

/**
 * Created by abadari on 18/10/2016.
 */

public class MyUtils {

    public static String verificaFormatacaoPostagem(String conteudo){
        String conteudoFormatado;
        if (conteudo.length()>=140){
            conteudoFormatado = conteudo.substring(0,127)+"[Ver mais...]";
            return conteudoFormatado;
        } else return conteudo;
    }

}
