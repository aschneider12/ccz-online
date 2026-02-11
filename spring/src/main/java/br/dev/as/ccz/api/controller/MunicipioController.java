package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.MunicipioDTO;
import br.dev.as.ccz.service.MunicipioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated(since = "10/02/2026", forRemoval = true)
@RestController
@RequestMapping("/api/v1/municipios")
@Tag(name = "Municípios", description = "Gerenciamento de cidades e códigos IBGE")
public class MunicipioController {

    @Autowired
    private MunicipioService service;

    @Operation(summary = "Lista todos os municípios cadastrados")
    @GetMapping
    public List<MunicipioDTO> listarTodos() {
        return service.listarTodos();
    }

    @Operation(summary = "Busca um município pelo ID único")
    @GetMapping("/{id}")
    public ResponseEntity<MunicipioDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo registro de município")
    @PostMapping
    public ResponseEntity<MunicipioDTO> criar(@Valid @RequestBody MunicipioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @Operation(summary = "Atualiza os dados de um município")
    @PutMapping("/{id}")
    public ResponseEntity<MunicipioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MunicipioDTO dto) {
        return service.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove um município do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}