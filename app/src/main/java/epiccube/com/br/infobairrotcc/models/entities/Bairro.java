package epiccube.com.br.infobairrotcc.models.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ivanc on 11/10/2016.
 */

public class Bairro implements Serializable{

    private String id;
    private String nome;
    private String cidade;
    private String estado;
    private String pais;
    private List<String> idUsuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<String> getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(List<String> idUsuario) {
        this.idUsuario = idUsuario;
    }
}
