package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto de transferência para dados dos marcadores do mapa")
public record MarkerDTO(

        @Schema(description = "ID da notificacao (key)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Titulo da notificação enviada", example = "Riscos de dengue")
        String titulo,

        @Size(max = 255)
        @Schema(description = "Breve relato da ocorrência na região", example = "Foram detectados muitos focos de dengue em áreas próximas, redobre o cuidado.")
        String descricao,

        @Schema(description = "Latitude", example = "-22.2080968")
        Double latitude,

        @Schema(description = "Longitude", example = "-54.8598216")
        Double longitude

) {}