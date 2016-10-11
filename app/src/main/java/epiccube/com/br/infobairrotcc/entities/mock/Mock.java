package epiccube.com.br.infobairrotcc.entities.mock;

import java.util.ArrayList;
import java.util.List;

import epiccube.com.br.infobairrotcc.entities.Postagem;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Mock {

    public static List<Postagem> postagens(){
        List<Postagem> postagens = new ArrayList<>();

        for (int i = 0; i<20000; i++){
            Postagem p = new Postagem();
            p.setTitulo("AAAAA");
            p.setConteudo("AAAAA");
            postagens.add(p);
        }

        return postagens;
    }

}
