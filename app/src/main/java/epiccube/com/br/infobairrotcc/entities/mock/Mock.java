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
            p.setTitulo("Comprei minha moto");
            p.setConteudo("Ganheir na loteria e comprei uma moto" +
                    "Ganheir na loteria e comprei uma moto" +
                    "Ganheir na loteria e comprei uma moto" +
                    "Ganheir na loteria e comprei uma moto" +
                    "Ganheir na loteria e comprei uma moto" +
                    "Ganheir na loteria e comprei uma moto");
            p.setCategoria("Evento");
            p.setNome("Mr. Mundiça");
            p.setUrlPerfilUsuario("https://pbs.twimg.com/profile_images/689937066520625153/EKPQ5hNO.jpg");
            postagens.add(p);
        }

        return postagens;
    }

}
