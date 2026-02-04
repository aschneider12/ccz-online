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

    @Column(name = "descricao", nullable = false, length = 255)
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

    public AlertaCczEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public MunicipioEntity getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioEntity municipio) {
        this.municipio = municipio;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public BigDecimal getCoordLatitude() {
        return coordLatitude;
    }

    public void setCoordLatitude(BigDecimal coordLatitude) {
        this.coordLatitude = coordLatitude;
    }

    public BigDecimal getCoordLongitude() {
        return coordLongitude;
    }

    public void setCoordLongitude(BigDecimal coordLongitude) {
        this.coordLongitude = coordLongitude;
    }

    public TipoNotificacaoEntity getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(TipoNotificacaoEntity tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public EspecieEntity getEspecie() {
        return especie;
    }

    public void setEspecie(EspecieEntity especie) {
        this.especie = especie;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}