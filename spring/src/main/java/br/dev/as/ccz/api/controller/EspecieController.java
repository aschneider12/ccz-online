package br.dev.as.ccz.api.controller;
import br.dev.as.ccz.api.dto.EspecieDTO;
import br.dev.as.ccz.service.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especies")
@Tag(name = "Espécies", description = "Endpoints para gerenciamento de espécies de animais")
public class EspecieController {

    @Autowired
    private EspecieService service;

    @Operation(summary = "Listar todas as espécies")
    @GetMapping
    public List<EspecieDTO> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Buscar espécie por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EspecieDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar nova espécie")
    @PostMapping
    public ResponseEntity<EspecieDTO> criar(@Valid @RequestBody EspecieDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @Operation(summary = "Atualizar uma espécie")
    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EspecieDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar uma espécie")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}