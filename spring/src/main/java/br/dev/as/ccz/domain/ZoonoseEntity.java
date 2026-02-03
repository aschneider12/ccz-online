package br.dev.as.ccz.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ZOONOSE")
public class ZoonoseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "notificacao_obrigatoria", nullable = true)
    private String notificacaoObrigatoria;
}