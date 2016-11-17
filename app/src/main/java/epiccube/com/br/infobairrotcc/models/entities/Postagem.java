package epiccube.com.br.infobairrotcc.models.entities;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Postagem implements Serializable{

    private String id;
    private String titulo;
    private String conteudo;
    private String categoria;
    private Integer curtidas;
    private Usuario usuario;
    private String estado;
    private String cidade;
    private String bairro;
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

    public Integer getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(Integer curtidas) {
        this.curtidas = curtidas;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public HashMap<String, String> getUrlFotosPostagens() {
        return urlFotosPostagens;
    }

    public void setUrlFotosPostagens(HashMap<String, String> urlFotosPostagens) {
        this.urlFotosPostagens = urlFotosPostagens;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("titulo", titulo);
        result.put("conteudo", conteudo);
        result.put("categoria", categoria);
        result.put("usuario", usuario);
        result.put("urlFotosPostagem", urlFotosPostagem);

        return result;
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

