package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Dados para criação de um alerta do CCZ")
public class AlertaCczCreateDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 25, message = "Título deve ter no máximo 25 caracteres")
    @Schema(description = "Título do alerta", example = "Foco de Dengue", maxLength = 25)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição detalhada do alerta", example = "Identificado foco de mosquito da dengue na região", maxLength = 255)
    private String descricao;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Schema(description = "Endereço do local", example = "Rua das Flores, 123", maxLength = 255)
    private String endereco;

    @NotNull(message = "Data é obrigatória")
    @Schema(description = "Data e hora do alerta", example = "2026-01-26T14:30:00")
    private LocalDateTime data;

    @NotNull(message = "Longitude é obrigatória")
    @Schema(description = "Coordenada de longitude", example = "-54.6295")
    private Double coordLongitude;

    @NotNull(message = "Latitude é obrigatória")
    @Schema(description = "Coordenada de latitude", example = "-20.4486")
    private Double coordLatitude;

    @NotNull(message = "Tipo de notificação é obrigatório")
    @Schema(description = "ID do tipo de notificação", example = "1")
    private Long tipoNotificacaoId;

    @Schema(description = "ID da espécie (opcional)", example = "1")
    private Long especieId;

    @NotNull(message = "Usuário é obrigatório")
    @Schema(description = "ID do usuário que criou o alerta", example = "1")
    private Long usuarioId;

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