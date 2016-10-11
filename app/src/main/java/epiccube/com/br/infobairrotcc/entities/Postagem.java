package epiccube.com.br.infobairrotcc.entities;

import java.util.List;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Postagem {

    private String id;
    private String titulo;
    private String conteudo;
    private String categoria;
    private String uidUsuario;
    private List<String> urlFotosPostagem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public List<String> getUrlFotosPostagem() {
        return urlFotosPostagem;
    }

    public void setUrlFotosPostagem(List<String> urlFotosPostagem) {
        this.urlFotosPostagem = urlFotosPostagem;
    }
}
