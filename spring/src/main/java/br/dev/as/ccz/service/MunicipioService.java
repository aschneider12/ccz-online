package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.MunicipioDTO;
import br.dev.as.ccz.domain.MunicipioEntity;
import br.dev.as.ccz.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class MunicipioService {

    @Autowired
    private MunicipioRepository repository;

    public List<MunicipioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<MunicipioDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    @Transactional
    public MunicipioDTO salvar(MunicipioDTO dto) {
        MunicipioEntity entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public Optional<MunicipioDTO> atualizar(Long id, MunicipioDTO dto) {
        return repository.findById(id).map(entity -> {
            entity.setDescricao(dto.descricao());
            entity.setUf(dto.uf());
            entity.setCodigoIbge(dto.codigoIbge());
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

    // Métodos auxiliares de conversão (Mappers)
    private MunicipioDTO toDTO(MunicipioEntity entity) {
        return new MunicipioDTO(
                entity.getId(),
                entity.getDescricao(),
                entity.getUf(),
                entity.getCodigoIbge()
        );
    }

    private MunicipioEntity toEntity(MunicipioDTO dto) {
        MunicipioEntity entity = new MunicipioEntity();
        // ID não é setado aqui pois é gerado pelo banco no POST
        entity.setDescricao(dto.descricao());
        entity.setUf(dto.uf());
        entity.setCodigoIbge(dto.codigoIbge());
        return entity;
    }
}