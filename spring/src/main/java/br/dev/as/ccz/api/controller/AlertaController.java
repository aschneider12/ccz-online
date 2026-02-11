package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.*;
import br.dev.as.ccz.service.AlertaCczService;
import br.dev.as.ccz.service.AlertaCidadaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
@RequestMapping("/api/v1/alertas")
@Tag(name = "Alertas", description = "Gerenciamento de alertas do Centro de Controle de Zoonoses")
public class AlertaController {

    private final AlertaCczService alertaCczService;
    private final AlertaCidadaoService  alertaCidadaoService;

    public AlertaController(AlertaCczService alertaCczService, AlertaCidadaoService alertaCidadaoService) {
        this.alertaCczService = alertaCczService;
        this.alertaCidadaoService = alertaCidadaoService;
    }

    @GetMapping("/proximidades")
    @Operation(summary = "Buscar solicitações próximas do usuário",
            description = "Retorna os alertas próximos ao cidadão ou solicitações próximas ao agente")

    public ResponseEntity<List<MarkerDTO>> buscarSolicitacoesProximasDoUsuario(
            @PathParam("perfil") String perfil,
            @PathParam("latitude") Double latitude,
            @PathParam("longitude") Double longitude,
            @PathParam("distancia") Integer distancia) {

        List<MarkerDTO> alertasProximos = null;

        if (perfil.equalsIgnoreCase("CIDADAO"))
            alertasProximos = alertaCczService.buscarAlertasRegiao(latitude, longitude, distancia);
        else
            if(perfil.equalsIgnoreCase("AGENTE_CCZ"))
                alertasProximos = alertaCidadaoService.buscarAlertasRegiao(latitude, longitude, distancia);

        return ResponseEntity.ok(alertasProximos);
    }
}