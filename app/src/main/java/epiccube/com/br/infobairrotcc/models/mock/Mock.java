package epiccube.com.br.infobairrotcc.models.mock;

import java.util.ArrayList;
import java.util.List;

import epiccube.com.br.infobairrotcc.models.entities.Postagem;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Mock {

    public static List<Postagem> postagens(){
        List<Postagem> postagens = new ArrayList<>();



        for (int i = 0; i<20000; i++){
            Postagem p = new Postagem();
            p.setTitulo("Comprei minha moto "+(i+1));
            p.setConteudo("Ganhei na loteria e comprei uma moto");
            p.setCategoria("Evento "+(i+1));
            p.setNome("Mr. Mundiça "+(i+1));
            p.setUrlPerfilUsuario("https://pbs.twimg.com/profile_images/689937066520625153/EKPQ5hNO.jpg");
            postagens.add(p);
        }

        return postagens;
    }

}
