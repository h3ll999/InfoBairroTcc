package epiccube.com.br.infobairrotcc.models.mock;

import java.util.ArrayList;
import java.util.List;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Mock {

    public static Usuario usuario(){
        Usuario u = new Usuario();
        u.setNome("Anderson Badari");
        u.setEmail("a@gmail.com");
        u.setPerfilUrl("https://lh3.googleusercontent.com/-JSXoTjIhI40/Unj1gNID1dI/AAAAAAAAACQ/" +
                "S3jyLYloQsELdlCkqKvizmHAaaYfHfNkwCEw/w139-h140-p/1374929_525980544142839_108209134_n.jpg");
        return u;
    }

    public static List<Postagem> postagens(){
        List<Postagem> postagens = new ArrayList<>();
        for (int i = 0; i<200; i++){

            if (i%2==0){ // com várias foto
                Postagem p = new Postagem();
                p.setTitulo("Title Impsum"+(i+1));
                p.setConteudo(SMALL_LOREM);
                p.setCategoria("Event Ipsum "+(i+1));
                p.setUrlFotosPostagem(new ArrayList<String>());
                p.getUrlFotosPostagem().add("http://simplesmenteberlim.com/wp-content/uploads/2012/08/PortaoDeBrandenburgo21.jpg");
                p.getUrlFotosPostagem().add("http://www.cvc.com.br/media/6016864/galeria-berlim-ilha-museus-creditos-thinksrock-458895805.jpg");
                p.getUrlFotosPostagem().add("http://www.circolare.com.br/uploads/2016/05/dsf-1.jpg");
                p.setUsuario(new Usuario());
                p.getUsuario().setNome("Name Impsum "+(i+1));
                p.getUsuario().setPerfilUrl("https://pbs.twimg.com/profile_images/689937066520625153/EKPQ5hNO.jpg");
                postagens.add(p);
            } else if(i%3==0) { // com 1 foto
                Postagem p = new Postagem();
                p.setTitulo("Title Impsum "+(i+1));
                p.setConteudo(HUGE_LOREM);
                p.setCategoria("Event Ipsum "+(i+1));
                p.setUrlFotosPostagem(new ArrayList<String>());
                p.getUrlFotosPostagem().add("http://www.cvc.com.br/media/6016864/galeria-berlim-ilha-museus-creditos-thinksrock-458895805.jpg");
                p.setUsuario(new Usuario());
                p.getUsuario().setNome("Name Impsum "+(i+1));
                p.getUsuario().setPerfilUrl("https://pbs.twimg.com/profile_images/689937066520625153/EKPQ5hNO.jpg");
                postagens.add(p);
            } else { // com nenhum foto
                Postagem p = new Postagem();
                p.setTitulo("Title Ipsum "+(i+1));
                p.setConteudo(HUGE_LOREM);
                p.setCategoria("Event Ipsum "+(i+1));
                p.setUrlFotosPostagem(new ArrayList<String>());
                p.setUsuario(new Usuario());
                p.getUsuario().setNome("Name Ipsum "+(i+1));
                p.getUsuario().setPerfilUrl("https://pbs.twimg.com/profile_images/689937066520625153/EKPQ5hNO.jpg");
                postagens.add(p);
            }

        }

        return postagens;
    }

    public static List<Integer> fotos(){
        List<Integer> fotos = new ArrayList<>();

        fotos.add(R.drawable.foto3);
        fotos.add(R.drawable.foto6);
        fotos.add(R.drawable.foto7);

        return fotos;
    }


    public static final String SMALL_LOREM = "Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    public static final String HUGE_LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";


}
