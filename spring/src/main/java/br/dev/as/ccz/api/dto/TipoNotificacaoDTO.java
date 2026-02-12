package br.dev.as.ccz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e resposta de Tipos de Notificação")
public record TipoNotificacaoDTO(

        @Schema(description = "ID único", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @NotBlank(message = "A descrição não pode ser vazia")
        @Size(max = 255)
        @Schema(description = "Descrição do tipo (ex: Alerta, Informativo)", example = "Infestação de escorpiões")
        String descricao,

        @NotBlank(message = "O nível de urgência é obrigatório")
        @Schema(description = "Nível de criticidade", example = "ALTA")
        String urgencia
) {}