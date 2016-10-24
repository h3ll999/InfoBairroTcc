package epiccube.com.br.infobairrotcc.eventos;

import epiccube.com.br.infobairrotcc.models.entities.Postagem;

/**
 * Created by abadari on 24/10/2016.
 */

public class EventoInseriuPostagemMockPostagem {
    private Postagem p;
    public EventoInseriuPostagemMockPostagem(Postagem p) {
        this.p = p;
    }
    public Postagem getP() {
        return p;
    }
    public void setP(Postagem p) {
        this.p = p;
    }
}
