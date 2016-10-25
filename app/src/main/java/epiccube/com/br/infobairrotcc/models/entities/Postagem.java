package epiccube.com.br.infobairrotcc.models.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Postagem implements Serializable{

    private String id;
    private String titulo;
    private String conteudo;
    private String categoria;
    private Usuario usuario;
    private List<String> urlFotosPostagem;
    private HashMap<String, String> urlFotosPostagens;

    public Postagem() {
    }

    public Postagem(String id, String titulo, String conteudo, String categoria, Usuario usuario, List<String> urlFotosPostagem) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoria = categoria;
        this.usuario = usuario;
        this.urlFotosPostagem = urlFotosPostagem;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<String> getUrlFotosPostagem() {
        return urlFotosPostagem;
    }

    public void setUrlFotosPostagem(List<String> urlFotosPostagem) {
        this.urlFotosPostagem = urlFotosPostagem;
    }

    public HashMap<String, String> getUrlFotosPostagens() {
        return urlFotosPostagens;
    }

    public void setUrlFotosPostagens(HashMap<String, String> urlFotosPostagens) {
        this.urlFotosPostagens = urlFotosPostagens;
    }

    @Override
    public String toString() {
        return "Postagem{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}

