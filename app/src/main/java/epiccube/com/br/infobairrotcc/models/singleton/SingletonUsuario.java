package epiccube.com.br.infobairrotcc.models.singleton;

import epiccube.com.br.infobairrotcc.models.entities.Usuario;

/**
 * Created by abadari on 17/10/2016.
 */

public class SingletonUsuario {

    public static SingletonUsuario instancia = null;
    public Usuario usuario;

    public SingletonUsuario() {
    }

    public static SingletonUsuario getInstancia() {
        if (instancia == null)
            instancia = new SingletonUsuario();
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
