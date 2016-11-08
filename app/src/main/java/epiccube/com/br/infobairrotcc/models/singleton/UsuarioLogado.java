package epiccube.com.br.infobairrotcc.models.singleton;

import epiccube.com.br.infobairrotcc.models.entities.Usuario;

/**
 * Created by abadari on 17/10/2016.
 */

public class UsuarioLogado {

    private static UsuarioLogado instancia = null;
    private Usuario usuario;

    public UsuarioLogado() {
    }

    public static UsuarioLogado getInstancia() {
        if (instancia == null){
            instancia = new UsuarioLogado();

        }
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void clear(){
        setUsuario(null);
    }
}
