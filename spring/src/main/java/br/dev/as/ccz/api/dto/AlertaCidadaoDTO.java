package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "AlertaCidadaoDTO",
        description = "DTO que representa um alerta enviado por um cidadão"
)
public class AlertaCidadaoDTO {

    @Schema(
            description = "Identificador do alerta",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Endereço onde ocorreu o alerta",
            example = "Rua das Flores, 123"
    )
    @Size(max = 255)
    private String endereco;

    @Schema(
            description = "ID do município",
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long municipioId;

    @Schema(
            description = "Descrição do alerta",
            example = "Avistamento de animal silvestre",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(max = 255)
    private String descricao;

    @Schema(
            description = "Data e hora do alerta",
            example = "2025-01-20T14:30:00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private LocalDateTime data;

    @Schema(
            description = "Coordenada de latitude",
            example = "-23.550520"
    )
    private BigDecimal coordLatitude;

    @Schema(
            description = "Coordenada de longitude",
            example = "-46.633308"
    )
    private BigDecimal coordLongitude;

    @Schema(
            description = "ID do tipo de notificação",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long tipoNotificacaoId;

    @Schema(
            description = "ID da espécie associada ao alerta (opcional)",
            example = "5",
            nullable = true
    )
    private Long especieId;

    @Schema(
            description = "ID do usuário que criou o alerta",
            example = "42",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long usuarioId;

    /* ==========================
       Getters e Setters
       ========================== */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
