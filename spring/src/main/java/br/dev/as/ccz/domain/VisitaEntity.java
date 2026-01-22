package br.dev.as.ccz.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "visita")
public class VisitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "morador", nullable = false, length = 255)
    private String morador;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    private UsuarioEntity agente;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    private EspecieEntity especie;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false)
    private MunicipioEntity municipio;

    @Column(name = "coord_latitude", precision = 10, scale = 0)
    private BigDecimal coordLatitude;

    @Column(name = "coord_longitude", precision = 10, scale = 0)
    private BigDecimal coordLongitude;
}