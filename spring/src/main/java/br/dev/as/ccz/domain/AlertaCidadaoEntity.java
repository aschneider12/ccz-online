package br.dev.as.ccz.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA_CIDADAO")
public class AlertaCidadaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false)
    private MunicipioEntity municipio;

    @Column(name = "descricao", nullable = false, unique = true, length = 255)
    private String descricao;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "coord_latitude", precision = 10, scale = 0)
    private BigDecimal coordLatitude;

    @Column(name = "coord_longitude", precision = 10, scale = 0)
    private BigDecimal coordLongitude;

    @ManyToOne
    @JoinColumn(name = "tipo_notificacao_id", nullable = false)
    private TipoNotificacaoEntity tipoNotificacao;

    @ManyToOne
    @JoinColumn(name = "especie_id")
    private EspecieEntity especie;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;
}