package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.AlertaCczCreateDTO;
import br.dev.as.ccz.api.dto.AlertaCczDTO;
import br.dev.as.ccz.api.dto.AlertaCczUpdateDTO;
import br.dev.as.ccz.service.AlertaCczService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas-ccz")
@Tag(name = "Alertas CCZ", description = "Gerenciamento de alertas do Centro de Controle de Zoonoses")
public class AlertaCczController {

    private final AlertaCczService alertaCczService;

    public AlertaCczController(AlertaCczService alertaCczService) {
        this.alertaCczService = alertaCczService;
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os alertas",
            description = "Retorna uma lista paginada de todos os alertas do CCZ"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AlertaCczDTO>> buscarAlertasDoAgente(
            @Parameter(
                    description = "ID do usuário",
                    example = "1"
            )
            @RequestParam Long usuarioId ) {

        List<AlertaCczDTO> alertas = alertaCczService.buscarAlertasCcz(usuarioId);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar alerta por ID",
            description = "Retorna os dados detalhados de um alerta específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerta encontrado",
                    content = @Content(schema = @Schema(implementation = AlertaCczDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AlertaCczDTO> buscarPorId(
            @Parameter(description = "ID do alerta", required = true, example = "1")
            @PathVariable Long id) {
        AlertaCczDTO alerta = alertaCczService.buscarPorId(id);
        return ResponseEntity.ok(alerta);
    }

    @GetMapping("/municipio/{municipioId}")
    @Operation(
            summary = "Buscar alertas por município",
            description = "Retorna uma lista paginada de alertas de um município específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<AlertaCczDTO>> buscarPorMunicipio(
            @Parameter(description = "ID do município", required = true, example = "1")
            @PathVariable Long municipioId,
            @PageableDefault(size = 20, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AlertaCczDTO> alertas = alertaCczService.buscarPorMunicipio(municipioId, pageable);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/tipo-notificacao/{tipoNotificacaoId}")
    @Operation(
            summary = "Buscar alertas por tipo de notificação",
            description = "Retorna uma lista paginada de alertas de um tipo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<AlertaCczDTO>> buscarPorTipoNotificacao(
            @Parameter(description = "ID do tipo de notificação", required = true, example = "1")
            @PathVariable Long tipoNotificacaoId,
            @PageableDefault(size = 20, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AlertaCczDTO> alertas = alertaCczService.buscarPorTipoNotificacao(tipoNotificacaoId, pageable);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/periodo")
    @Operation(
            summary = "Buscar alertas por período",
            description = "Retorna uma lista paginada de alertas dentro de um período especificado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<AlertaCczDTO>> buscarPorPeriodo(
            @Parameter(description = "Data de início (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true, example = "2026-01-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @Parameter(description = "Data de fim (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true, example = "2026-01-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 20, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AlertaCczDTO> alertas = alertaCczService.buscarPorPeriodo(dataInicio, dataFim, pageable);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/recentes")
    @Operation(
            summary = "Buscar alertas recentes",
            description = "Retorna os alertas dos últimos 7 dias ordenados por data decrescente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas recentes retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AlertaCczDTO>> buscarAlertasRecentes() {
        List<AlertaCczDTO> alertas = alertaCczService.buscarAlertasRecentes();
        return ResponseEntity.ok(alertas);
    }

    @PostMapping
    @Operation(
            summary = "Criar novo alerta",
            description = "Cadastra um novo alerta do CCZ no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Alerta criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlertaCczDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Descrição já cadastrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AlertaCczDTO> criarNovoAlerta(
            @Valid @RequestBody AlertaCczCreateDTO createDTO) {
        AlertaCczDTO alertaCriado = alertaCczService.savarAlerta(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaCriado);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar alerta",
            description = "Atualiza os dados de um alerta existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerta atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AlertaCczDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado"),
            @ApiResponse(responseCode = "409", description = "Descrição já cadastrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AlertaCczDTO> atualizar(
            @Parameter(description = "ID do alerta", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody AlertaCczUpdateDTO updateDTO) {
        AlertaCczDTO alertaAtualizado = alertaCczService.atualizar(id, updateDTO);
        return ResponseEntity.ok(alertaAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir alerta",
            description = "Remove um alerta do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do alerta", required = true, example = "1")
            @PathVariable Long id) {
        alertaCczService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estatisticas/municipio/{municipioId}/total")
    @Operation(
            summary = "Contar alertas por município",
            description = "Retorna o total de alertas de um município específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de alertas retornado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Long> contarPorMunicipio(
            @Parameter(description = "ID do município", required = true, example = "1")
            @PathVariable Long municipioId) {
        Long total = alertaCczService.contarPorMunicipio(municipioId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/tipo-notificacao/{tipoNotificacaoId}/total")
    @Operation(
            summary = "Contar alertas por tipo de notificação",
            description = "Retorna o total de alertas de um tipo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de alertas retornado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Long> contarPorTipoNotificacao(
            @Parameter(description = "ID do tipo de notificação", required = true, example = "1")
            @PathVariable Long tipoNotificacaoId) {
        Long total = alertaCczService.contarPorTipoNotificacao(tipoNotificacaoId);
        return ResponseEntity.ok(total);
    }
}