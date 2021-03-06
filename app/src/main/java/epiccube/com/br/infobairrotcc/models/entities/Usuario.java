package epiccube.com.br.infobairrotcc.models.entities;

import com.google.android.gms.common.api.BooleanResult;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Usuario implements Serializable {

    private String uid;
    private String nome;
    private String email;
    private String senha;
    private String perfilUrl;
    @Exclude
    private Boolean permissaoPostagem;
    private List<String> postagensId;
    @Exclude
    private String bairroAtualId;
    private String bairroOrigemId;
    @Exclude
    private String cidadeAtualId;
    private String cidadeOrigemId;
    @Exclude
    private String estadoAtualId;
    private String estadoOrigemId;
    private Date ultimaTrocaLocal;
    @Exclude
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

    public Boolean getPermissaoPostagem() {
        return permissaoPostagem;
    }

    public void setPermissaoPostagem(Boolean permissaoPostagem) {
        this.permissaoPostagem = permissaoPostagem;
    }

    public List<String> getPostagensId() {
        return postagensId;
    }

    public void setPostagensId(List<String> postagensId) {
        this.postagensId = postagensId;
    }

    @Exclude
    public String getBairroAtualId() {
        return bairroAtualId;
    }

    @Exclude
    public void setBairroAtualId(String bairroAtualId) {
        this.bairroAtualId = bairroAtualId;
    }

    public String getBairroOrigemId() {
        return bairroOrigemId;
    }

    public void setBairroOrigemId(String bairroOrigemId) {
        this.bairroOrigemId = bairroOrigemId;
    }

    @Exclude
    public String getCidadeAtualId() {
        return cidadeAtualId;
    }

    @Exclude
    public void setCidadeAtualId(String cidadeAtualId) {
        this.cidadeAtualId = cidadeAtualId;
    }

    public String getCidadeOrigemId() {
        return cidadeOrigemId;
    }

    public void setCidadeOrigemId(String cidadeOrigemId) {
        this.cidadeOrigemId = cidadeOrigemId;
    }

    @Exclude
    public String getEstadoAtualId() {
        return estadoAtualId;
    }

    @Exclude
    public void setEstadoAtualId(String estadoAtualId) {
        this.estadoAtualId = estadoAtualId;
    }

    public String getEstadoOrigemId() {
        return estadoOrigemId;
    }

    public void setEstadoOrigemId(String estadoOrigemId) {
        this.estadoOrigemId = estadoOrigemId;
    }

    public Date getUltimaTrocaLocal() {
        return ultimaTrocaLocal;
    }

    public void setUltimaTrocaLocal(Date ultimaTrocaLocal) {
        this.ultimaTrocaLocal = ultimaTrocaLocal;
    }

    @Exclude
    public Double[] getLatitudeLongitude() {
        return latitudeLongitude;
    }

    @Exclude
    public void setLatitudeLongitude(Double[] latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("email", email);
        result.put("senha", senha);
        result.put("perfilUrl", perfilUrl);
        result.put("bairroOrigemId", bairroOrigemId);
        result.put("cidadeOrigemId", cidadeOrigemId);
        result.put("estadoOrigemId", estadoOrigemId);
        result.put("ultimaTrocaLocal", ultimaTrocaLocal);

        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", perfilUrl='" + perfilUrl + '\'' +
                ", permissaoPostagem=" + permissaoPostagem +
                ", bairroAtualId='" + bairroAtualId + '\'' +
                ", bairroOrigemId='" + bairroOrigemId + '\'' +
                ", cidadeAtualId='" + cidadeAtualId + '\'' +
                ", cidadeOrigemId='" + cidadeOrigemId + '\'' +
                ", estadoAtualId='" + estadoAtualId + '\'' +
                ", estadoOrigemId='" + estadoOrigemId + '\'' +
                '}';
    }
}
