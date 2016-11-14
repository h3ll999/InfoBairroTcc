package epiccube.com.br.infobairrotcc.models.contantes;

import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.utils.MyUtils;

/**
 * Created by Anderson on 13/10/2016.
 */

public class Constantes {

    public static final String INTENT_POSTAGEM = "INTENT_POSTAGEM";

    public static final String LISTA_FOTOS = "LISTA_FOTOS";

    public static final String USUARIO = "USUARIO";
    public static final String PERFIL = "PERFIL";

    public static final String POSTAGENS = "POSTAGENS";
    public static final String POSTAGENS_SEM_FILTRO = "POSTAGENS_SEM_FILTRO";

    public static final String POSTAGENS_EVENTOS = "POSTAGENS_EVENTOS";
    public static final String POSTAGENS_NOTICIAS = "POSTAGENS_NOTICIAS";
    public static final String POSTAGENS_SERVICOS = "POSTAGENS_SERVICOS";
    public static final String POSTAGENS_ORIGEM(){
        return MyUtils.concatenaCaminhoOrigem();
    }

    public static final String POSTAGENS_EVENTOS(){
        return MyUtils.concatenaCaminhoAtual()+"/"+POSTAGENS_EVENTOS;
    }
    public static final String POSTAGENS_NOTICIAS(){
        return MyUtils.concatenaCaminhoAtual()+"/"+POSTAGENS_NOTICIAS;
    }
    public static final String POSTAGENS_SERVICOS(){
        return MyUtils.concatenaCaminhoAtual()+"/"+POSTAGENS_SERVICOS;
    }

    public static final String NOTICIAS = "Notícias";
    public static final String EVENTOS = "Eventos";
    public static final String SERVICOS = "Serviços";

    public static final String REPOSITORIO_FOTOS = "gs://infobairrotcc-fa6f9.appspot.com";

    public static String formataPostagens(String[] locais, String filtro){
        return Constantes.POSTAGENS+"/"+locais[0]+"/"+locais[1]+"/"+locais[2]+"/"+filtro;
    }


}
