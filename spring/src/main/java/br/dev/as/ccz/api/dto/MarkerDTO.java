package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto de transferência para dados dos marcadores do mapa")
public record MarkerDTO(

        @Schema(description = "ID da notificacao", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255)
        @Schema(description = "Nome do município", example = "São Paulo")
        String descricao,

        @NotBlank(message = "A UF é obrigatória")
        @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
        @Schema(description = "Sigla do estado", example = "SP")
        String uf,

        @Schema(description = "Código IBGE do município", example = "3550308")
        String codigoIbge
) {}