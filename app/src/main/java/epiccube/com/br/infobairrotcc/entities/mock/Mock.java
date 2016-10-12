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



        for (int i = 0; i<200; i++){
            Postagem p = new Postagem();
            p.setTitulo("Comprei minha moto");
            p.setConteudo("Ganheir na loteria e comprei uma moto");
            p.setCategoria("Evento");
            p.setNome("Jose bunda");
            p.setUrlPerfilUsuario("http://www.pi-cube.com/wp-content/uploads/2015/04/team-placeholder.jpg");
            postagens.add(p);
        }

        return postagens;
    }

}
