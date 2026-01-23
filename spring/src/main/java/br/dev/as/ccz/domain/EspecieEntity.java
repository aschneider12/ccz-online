package br.dev.as.ccz.domain;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ESPECIE")
public class EspecieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true, length = 255)
    private String descricao;

    @Column(name = "imagem", length = 255)
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "zoonose_id")
    private ZoonoseEntity zoonose;

    public EspecieEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public ZoonoseEntity getZoonose() {
        return zoonose;
    }

    public void setZoonose(ZoonoseEntity zoonose) {
        this.zoonose = zoonose;
    }
}