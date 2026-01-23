package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.TipoNotificacaoDTO;
import br.dev.as.ccz.service.TipoNotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-notificacao")
@Tag(name = "Tipos de Notificação", description = "Gerenciamento das categorias de alertas do sistema")
public class TipoNotificacaoController {

    @Autowired
    private TipoNotificacaoService service;

    @Operation(summary = "Listar todos os tipos")
    @GetMapping
    public List<TipoNotificacaoDTO> listar() {
        return service.listarTodos();
    }

    @Operation(summary = "Obter detalhes de um tipo específico")
    @GetMapping("/{id}")
    public ResponseEntity<TipoNotificacaoDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo tipo de notificação")
    @PostMapping
    public ResponseEntity<TipoNotificacaoDTO> criar(@Valid @RequestBody TipoNotificacaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @Operation(summary = "Atualizar um tipo existente")
    @PutMapping("/{id}")
    public ResponseEntity<TipoNotificacaoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TipoNotificacaoDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remover um tipo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}