package br.dev.as.ccz.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cartao_sus", nullable = false)
    private Long cartaoSus;

    @Column(name = "perfil", nullable = false, length = 255)
    private String perfil = "25";

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long id, String nome, Long cartaoSus, String perfil, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cartaoSus = cartaoSus;
        this.perfil = perfil;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(Long cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}