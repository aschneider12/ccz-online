package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Dados de resposta de um alerta do CCZ")
public class AlertaCczDTO {

    @Schema(description = "ID do alerta", example = "1")
    private Long id;

    @Schema(description = "Título do alerta", example = "Foco de Dengue")
    private String titulo;

    @Schema(description = "Descrição detalhada do alerta", example = "Identificado foco de mosquito da dengue na região")
    private String descricao;

    @Schema(description = "Endereço do local", example = "Rua das Flores, 123")
    private String endereco;

    @Schema(description = "Data e hora do alerta", example = "2026-01-26T14:30:00")
    private LocalDateTime data;

    @Schema(description = "Coordenada de longitude", example = "-54.6295")
    private Double coordLongitude;

    @Schema(description = "Coordenada de latitude", example = "-20.4486")
    private Double coordLatitude;

    @Schema(description = "Tipo de notificação")
    private Long tipoNotificacaoId;

    @Schema(description = "Espécie identificada")
    private Long especieId;

    @Schema(description = "Usuário que criou o alerta")
    private Long usuarioId;

    // Getters and Setters
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getCoordLongitude() {
        return coordLongitude;
    }

    public void setCoordLongitude(Double coordLongitude) {
        this.coordLongitude = coordLongitude;
    }

    public Double getCoordLatitude() {
        return coordLatitude;
    }

    public void setCoordLatitude(Double coordLatitude) {
        this.coordLatitude = coordLatitude;
    }

    public Long getTipoNotificacaoId() {
        return tipoNotificacaoId;
    }

    public void setTipoNotificacaoId(Long tipoNotificacaoId) {
        this.tipoNotificacaoId = tipoNotificacaoId;
    }

    public Long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(Long especieId) {
        this.especieId = especieId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
