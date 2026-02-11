package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.AlertaCidadaoDTO;
import br.dev.as.ccz.service.AlertaCidadaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas-cidadao")
@Tag(
        name = "Alertas do Cidadão",
        description = "Endpoints responsáveis pelo gerenciamento de alertas cadastrados por cidadãos"
)
public class AlertaCidadaoController {

    private final AlertaCidadaoService alertaCidadaoService;

    public AlertaCidadaoController(AlertaCidadaoService alertaCidadaoService) {
        this.alertaCidadaoService = alertaCidadaoService;
    }

    @Operation(
            summary = "Criar alerta do cidadão",
            description = "Cria um novo alerta associado a um usuário, município, tipo de notificação e opcionalmente uma espécie"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Alerta criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlertaCidadaoDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    }
    )
    @PostMapping
    public ResponseEntity<AlertaCidadaoDTO> criarAlerta(
            @Valid @RequestBody AlertaCidadaoDTO dto) {

        AlertaCidadaoDTO criado = alertaCidadaoService.salvarAlertaCidadao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Buscar alerta por ID",
            description = "Retorna os dados de um alerta do cidadão a partir do ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerta encontrado",
                    content = @Content(schema = @Schema(implementation = AlertaCidadaoDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<AlertaCidadaoDTO> buscarPorId(
            @Parameter(description = "ID do alerta", example = "10")
            @PathVariable Long id) {

        return ResponseEntity.ok(alertaCidadaoService.buscarPorId(id));
    }

    @Operation(
            summary = "Buscar solicitações",
            description = "Retorna a lista de solicitações"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitações encontradas",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AlertaCidadaoDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma solicitação encontrada para os filtros",
                    content = @Content
            )
    })

    @GetMapping
    public ResponseEntity<List<AlertaCidadaoDTO>> buscarAlertasDoCidadao(
            @Parameter(
                    description = "ID do usuário",
                    example = "123"
            )
            @RequestParam Long usuarioId
    ) {
        return ResponseEntity.ok(
                alertaCidadaoService.buscarAlertas(usuarioId)
        );
    }

    @Operation(
            summary = "Atualizar alerta do cidadão",
            description = "Atualiza os dados de um alerta existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerta atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlertaCidadaoDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })

    @PutMapping("/{id}")
    public ResponseEntity<AlertaCidadaoDTO> atualizar(
            @Parameter(description = "ID do alerta", example = "10")
            @PathVariable Long id,
            @Valid @RequestBody AlertaCidadaoDTO dto) {

        return ResponseEntity.ok(alertaCidadaoService.atualizar(id, dto));
    }

    @Operation(
            summary = "Excluir alerta do cidadão",
            description = "Remove um alerta do cidadão pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do alerta", example = "10")
            @PathVariable Long id) {

        alertaCidadaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
