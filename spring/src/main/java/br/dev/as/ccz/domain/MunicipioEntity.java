package br.dev.as.ccz.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "MUNICIPIO")
public class MunicipioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "uf", nullable = false, length = 255)
    private String uf;

    @Column(name = "codigo_ibge")
    private String codigoIbge;

    public MunicipioEntity() {

    }

    public MunicipioEntity(Long id, String descricao, String uf, String codigoIbge) {
        this.id = id;
        this.descricao = descricao;
        this.uf = uf;
        this.codigoIbge = codigoIbge;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}