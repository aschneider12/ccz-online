package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.TipoNotificacaoDTO;
import br.dev.as.ccz.domain.TipoNotificacaoEntity;
import br.dev.as.ccz.repository.TipoNotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TipoNotificacaoService {

    @Autowired
    private TipoNotificacaoRepository repository;

    public List<TipoNotificacaoDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<TipoNotificacaoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    @Transactional
    public TipoNotificacaoDTO salvar(TipoNotificacaoDTO dto) {
        TipoNotificacaoEntity entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public Optional<TipoNotificacaoDTO> atualizar(Long id, TipoNotificacaoDTO dto) {
        return repository.findById(id).map(entity -> {
            entity.setDescricao(dto.descricao());
            entity.setUrgencia(dto.urgencia());
            return toDTO(repository.save(entity));
        });
    }

    @Transactional
    public boolean excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private TipoNotificacaoDTO toDTO(TipoNotificacaoEntity entity) {
        return new TipoNotificacaoDTO(entity.getId(), entity.getDescricao(), entity.getUrgencia());
    }

    private TipoNotificacaoEntity toEntity(TipoNotificacaoDTO dto) {
        TipoNotificacaoEntity entity = new TipoNotificacaoEntity();
        entity.setDescricao(dto.descricao());
        entity.setUrgencia(dto.urgencia());
        return entity;
    }
}