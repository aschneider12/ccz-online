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

}