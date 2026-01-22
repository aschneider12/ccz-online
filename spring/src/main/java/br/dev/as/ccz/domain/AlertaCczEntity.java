package br.dev.as.ccz.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA_CCZ")
public class AlertaCczEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 25)
    private String titulo;

    @Column(name = "descricao", nullable = false, unique = true, length = 255)
    private String descricao;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false)
    private MunicipioEntity municipio;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "coord_latitude", nullable = false, precision = 10, scale = 0)
    private BigDecimal coordLatitude;

    @Column(name = "coord_longitude", nullable = false, precision = 10, scale = 0)
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