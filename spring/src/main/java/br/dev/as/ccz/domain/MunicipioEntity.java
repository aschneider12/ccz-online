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
    private Long codigoIbge;
}