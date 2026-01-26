package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Dados para atualização de um alerta do CCZ")
public class AlertaCczUpdateDTO {

    @Size(max = 25, message = "Título deve ter no máximo 25 caracteres")
    @Schema(description = "Título do alerta", example = "Foco de Dengue Controlado", maxLength = 25)
    private String titulo;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição detalhada do alerta", example = "Foco controlado após intervenção", maxLength = 255)
    private String descricao;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Schema(description = "Endereço do local", example = "Rua das Flores, 123", maxLength = 255)
    private String endereco;

    @Schema(description = "ID do município", example = "1")
    private Long municipioId;

    @Schema(description = "Data e hora do alerta", example = "2026-01-26T14:30:00")
    private LocalDateTime data;

    @Schema(description = "Coordenada de latitude", example = "-20.4486")
    private BigDecimal coordLatitude;

    @Schema(description = "Coordenada de longitude", example = "-54.6295")
    private BigDecimal coordLongitude;

    @Schema(description = "ID do tipo de notificação", example = "1")
    private Long tipoNotificacaoId;

    @Schema(description = "ID da espécie (opcional)", example = "1")
    private Long especieId;

    // Getters and Setters
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

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
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
}