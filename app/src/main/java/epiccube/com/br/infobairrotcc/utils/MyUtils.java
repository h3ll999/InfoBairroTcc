package epiccube.com.br.infobairrotcc.utils;

import android.app.Activity;
import android.util.Log;

import java.text.Normalizer;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;

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
        // String era "São Paulo" e agora ficou "SAOPAULO"
    }

    public static String removeAcentosEspacos(String input){
        if (input==null) {
            return "";// senão CRASHA o próximo método...
        }
        else {
            return removeAcentosEspacosP(input);
        }
    }

    public static String segundoNome(String nome){
        char[] nomeChar = nome.toCharArray();
        char[] primeiroNome = new char[nome.length()]; // todo tamanho está tosco

        for(int i = 0; i < nomeChar.length; i++){
            if (nomeChar[i] == ' '){
                //teste
                //segundoNome(nome);
                //fim teste
                break;
            }
            primeiroNome[i] = nomeChar[i];
        }



        // todo implementar pra pegar a primeira letra do sobrenome

        Log.e("primeiroNome",new String(primeiroNome));
        return new String(primeiroNome).trim();

    }

    // TODO algoritmo está estranho, mas funciona
    public static String primeiroNome(String nome){

        String nomeAbreviado = null;
        boolean naoTemSobrenome = true;

        char[] nomeChar = nome.toCharArray();
        char[] nomeSobrenome = new char[nome.length()]; // todo tamanho está tosco
        int cont = 0;

        for(int i = 0; i < nomeChar.length; i++){

            if (cont < 1){
                if(nomeChar[i] == ' ') {
                    nomeSobrenome[i] = nomeChar[i];
                    cont++;
                    i++;
                }
                nomeSobrenome[i] = nomeChar[i];
            } else {
                naoTemSobrenome = false;
                nomeAbreviado = new String(nomeSobrenome).trim()+".";
                break;
            }
        }

        if(naoTemSobrenome){
            nomeAbreviado = new String(nomeSobrenome).trim();
        }

        Log.e("nomeSobrenome",new String(nomeSobrenome)+" | "+cont);
        return nomeAbreviado;
    }


    /*public static String primeiroNome(String nome){

        char[] nomeChar = nome.toCharArray();
        char[] nomeSobrenome = new char[nome.length()]; // todo tamanho está tosco
        int cont = 0;

        for(int i = 0; i < nomeChar.length; i++){

            if (nomeChar[i] == ' '){
                if(cont >= 1){
                    break;
                }
                cont++;
                nomeSobrenome[i] = nomeChar[i];
                continue;
            }
            nomeSobrenome[i] = nomeChar[i];
        }

        Log.e("nomeSobrenome",new String(nomeSobrenome)+" | "+cont);
        return new String(nomeSobrenome).trim();
    }*/


    public static String concatenaCaminhoAtual(){

        String caminho =
                MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getEstadoAtualId())+"/"+
                        MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getCidadeAtualId())+"/"+
                            MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getBairroAtualId());

        return caminho;

    }

    public static String concatenaCaminhoOrigem(){
        String caminho =
                MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getEstadoOrigemId())+"/"+
                        MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getCidadeOrigemId())+"/"+
                            MyUtils.removeAcentosEspacos(UsuarioLogado.getInstancia().getUsuario().getBairroOrigemId());

        return caminho;
    }



}
