package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.UsuarioCreateDTO;
import br.dev.as.ccz.api.dto.UsuarioDTO;
import br.dev.as.ccz.api.dto.UsuarioUpdateDTO;
import br.dev.as.ccz.api.exception.CpfJaCadastradoException;
import br.dev.as.ccz.api.exception.UsuarioNaoEncontradoException;
import br.dev.as.ccz.domain.UsuarioEntity;
import br.dev.as.ccz.enums.PerfilEnum;
import br.dev.as.ccz.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lista todos os usuários com paginação
     *
     * @param pageable Configurações de paginação e ordenação
     * @return Página de usuários
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    /**
     * Busca um usuário por ID
     *
     * @param id ID do usuário
     * @return DTO do usuário encontrado
     * @throws UsuarioNaoEncontradoException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + id));
        return converterParaDTO(usuario);
    }

    /**
     * Busca um usuário por CPF
     *
     * @param cpf CPF do usuário
     * @return DTO do usuário encontrado
     * @throws UsuarioNaoEncontradoException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorCpf(String cpf) {
        UsuarioEntity usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado para o CPF informado"));
        return converterParaDTO(usuario);
    }

    /**
     * Cria um novo usuário
     *
     * @param usuarioCreateDTO Dados do usuário a ser criado
     * @return DTO do usuário criado
     * @throws CpfJaCadastradoException se o CPF já estiver cadastrado
     */
    @Transactional
    public UsuarioDTO criar(UsuarioCreateDTO usuarioCreateDTO) {
        // Verifica se o CPF já está cadastrado
        if (usuarioRepository.existsByCpf(usuarioCreateDTO.getCpf())) {
            throw new CpfJaCadastradoException("CPF já cadastrado no sistema");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setCartaoSus(usuarioCreateDTO.getCartaoSus());
        usuario.setPerfil(usuarioCreateDTO.getPerfil().toString());
        usuario.setCpf(usuarioCreateDTO.getCpf());

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuario);
        return converterParaDTO(usuarioSalvo);
    }

    /**
     * Atualiza um usuário existente
     *
     * @param id               ID do usuário a ser atualizado
     * @param usuarioUpdateDTO Dados para atualização
     * @return DTO do usuário atualizado
     * @throws UsuarioNaoEncontradoException se o usuário não for encontrado
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + id));

        // Atualiza apenas os campos que foram informados
        if (usuarioUpdateDTO.getNome() != null && !usuarioUpdateDTO.getNome().isBlank()) {
            usuario.setNome(usuarioUpdateDTO.getNome());
        }

        if (usuarioUpdateDTO.getCartaoSus() != null) {
            usuario.setCartaoSus(usuarioUpdateDTO.getCartaoSus());
        }

        if (usuarioUpdateDTO.getPerfil() != null) {
            usuario.setPerfil(usuarioUpdateDTO.getPerfil().toString());
        }

        UsuarioEntity usuarioAtualizado = usuarioRepository.save(usuario);
        return converterParaDTO(usuarioAtualizado);
    }

    /**
     * Exclui um usuário
     *
     * @param id ID do usuário a ser excluído
     * @throws UsuarioNaoEncontradoException se o usuário não for encontrado
     */
    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Converte uma entidade UsuarioEntity para UsuarioDTO
     *
     * @param usuario Entidade a ser convertida
     * @return DTO convertido
     */
    private UsuarioDTO converterParaDTO(UsuarioEntity usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCartaoSus(usuario.getCartaoSus());
        dto.setPerfil(PerfilEnum.valueOf(usuario.getPerfil()));
        dto.setCpf(usuario.getCpf());
        return dto;
    }


}