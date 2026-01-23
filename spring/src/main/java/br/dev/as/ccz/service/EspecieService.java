package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.EspecieDTO;
import br.dev.as.ccz.api.mapper.EspecieMapper;
import br.dev.as.ccz.domain.EspecieEntity;
import br.dev.as.ccz.repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository repository;

    @Autowired
    private EspecieMapper mapper; // Injetando o mapper gerado

    public List<EspecieDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<EspecieDTO> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Transactional
    public EspecieDTO salvar(EspecieDTO dto) {
        EspecieEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public Optional<EspecieDTO> atualizar(Long id, EspecieDTO dto) {
        return repository.findById(id).map(entity -> {
            // O MapStruct pode atualizar campos automaticamente se configurado
            entity.setDescricao(dto.descricao());
            entity.setImagem(dto.imagem());
            return mapper.toDTO(repository.save(entity));
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
}