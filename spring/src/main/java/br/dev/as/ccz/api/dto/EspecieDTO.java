package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para representação de Espécies")
public record EspecieDTO(

        @Schema(description = "ID único da espécie", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255)
        @Schema(description = "Nome ou descrição da espécie", example = "Canina")
        String descricao,

        @Size(max = 255)
        @Schema(description = "Caminho ou URL da imagem representativa", example = "/uploads/especies/dog.png")
        String imagem
) {}