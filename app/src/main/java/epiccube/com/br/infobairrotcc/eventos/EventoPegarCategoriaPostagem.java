package epiccube.com.br.infobairrotcc.eventos;

/**
 * Created by abadari on 24/10/2016.
 */

public class EventoPegarCategoriaPostagem {
    private String categoria;
    public EventoPegarCategoriaPostagem(String categoria) {
        this.categoria = categoria;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void clear(){
        categoria=null;
    }
}