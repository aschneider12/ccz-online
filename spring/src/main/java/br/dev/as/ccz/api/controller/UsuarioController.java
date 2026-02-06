package br.dev.as.ccz.api.controller;

import br.dev.as.ccz.api.dto.*;
import br.dev.as.ccz.service.UsuarioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth/login")
    @Tag(name = "Autenticação")
    @Operation(summary = "Realizar login no sistema",
            description = "Autentica um usuário através do CPF e retorna os dados do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsuarioDTO usuario = usuarioService.buscarPorCpf(loginRequest.getCpf());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos os usuários",
            description = "Retorna uma lista paginada de todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UsuarioDTO>> listarUsuarios(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            @Parameter(description = "Parâmetros de paginação e ordenação") Pageable pageable) {
        Page<UsuarioDTO> usuarios = usuarioService.listarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Criar novo usuário",
            description = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioDTO usuarioCriado = usuarioService.criar(usuarioCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/usuarios/{id}")
    @Operation(summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioDTO usuarioAtualizado = usuarioService.atualizar(id, usuarioUpdateDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Excluir usuário",
            description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluirUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/cpf/{cpf}")
    @Operation(summary = "Buscar usuário por CPF",
            description = "Retorna os dados de um usuário através do CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorCpf(
            @Parameter(description = "CPF do usuário (apenas números)", required = true)
            @PathVariable String cpf) {
        UsuarioDTO usuario = usuarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios/solicitacoes-proximas")
    @Operation(summary = "Buscar solicitações próximas do usuário",
            description = "Retorna os alertas próximas ao cidadão ou solicitações próximas ao agente")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
//                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
//            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
//            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
    public ResponseEntity<List<MarkerDTO>> buscarSolicitacoesProximasDoUsuario(
            @PathParam("id") Long idUsuario,
            @PathParam("latitude") String latitude,
            @PathParam("longitude") String longitude,
            @PathParam("distancia") Integer distancia) {

        UsuarioDTO usuario = usuarioService.buscarPorId(idUsuario);
        if(usuario.getPerfil().equals("cidadao"))

            System.out.println("Buscar alertas proximos ao cidadao");
        else
            System.out.println("Buscar alertas enviado proximos do agente");
//<Marker usar o marker como DTO
//                key={marker.id}
//        coordinate={{
//                latitude: marker.latitude,
//                longitude: marker.longitude,
//            }}
//        title={marker.title}
//        description={marker.description}
//                />
        return ResponseEntity.ok(null);
    }
}