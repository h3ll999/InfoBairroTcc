package epiccube.com.br.infobairrotcc.entities;

import java.util.List;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Usuario {

    private String uid;
    private String nome;
    private String email;
    private String senha;
    private String perfilUrl;
    private List<String> postagensId;
    private String bairroAtualId;
    private String bairroOrigemId;
    private Double[] latitudeLongitude;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfilUrl() {
        return perfilUrl;
    }

    public void setPerfilUrl(String perfilUrl) {
        this.perfilUrl = perfilUrl;
    }

    public List<String> getPostagensId() {
        return postagensId;
    }

    public void setPostagensId(List<String> postagensId) {
        this.postagensId = postagensId;
    }

    public String getBairroAtualId() {
        return bairroAtualId;
    }

    public void setBairroAtualId(String bairroAtualId) {
        this.bairroAtualId = bairroAtualId;
    }

    public String getBairroOrigemId() {
        return bairroOrigemId;
    }

    public void setBairroOrigemId(String bairroOrigemId) {
        this.bairroOrigemId = bairroOrigemId;
    }

    public Double[] getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public void setLatitudeLongitude(Double[] latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }
}
