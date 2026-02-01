package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.AlertaCidadaoDTO;
import br.dev.as.ccz.domain.AlertaCidadaoEntity;
import br.dev.as.ccz.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaCidadaoService {

    private final AlertaCidadaoRepository alertaRepository;
    private final MunicipioRepository municipioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoNotificacaoRepository tipoNotificacaoRepository;
    private final EspecieRepository especieRepository;

    public AlertaCidadaoService(
            AlertaCidadaoRepository alertaRepository,
            MunicipioRepository municipioRepository,
            UsuarioRepository usuarioRepository,
            TipoNotificacaoRepository tipoNotificacaoRepository,
            EspecieRepository especieRepository) {

        this.alertaRepository = alertaRepository;
        this.municipioRepository = municipioRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoNotificacaoRepository = tipoNotificacaoRepository;
        this.especieRepository = especieRepository;
    }

    @Transactional
    public AlertaCidadaoDTO criar(AlertaCidadaoDTO dto) {

//        if (alertaRepository.existsByDescricao(dto.getDescricao())) {
//            throw new IllegalArgumentException("Já existe um alerta com essa descrição");
//        }

        AlertaCidadaoEntity entity = new AlertaCidadaoEntity();
        mapearDtoParaEntity(dto, entity);

        entity.setData(
                dto.getData() != null ? dto.getData() : LocalDateTime.now()
        );

        AlertaCidadaoEntity salvo = alertaRepository.save(entity);
        return mapearEntityParaDto(salvo);
    }

    @Transactional(readOnly = true)
    public AlertaCidadaoDTO buscarPorId(Long id) {
        return alertaRepository.findById(id)
                .map(this::mapearEntityParaDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Alerta não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<AlertaCidadaoDTO> listar() {
        return alertaRepository.findAll()
                .stream()
                .map(this::mapearEntityParaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlertaCidadaoDTO atualizar(Long id, AlertaCidadaoDTO dto) {

        AlertaCidadaoEntity entity = alertaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Alerta não encontrado"));

        mapearDtoParaEntity(dto, entity);

        return mapearEntityParaDto(alertaRepository.save(entity));
    }

    @Transactional
    public void excluir(Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new EntityNotFoundException("Alerta não encontrado");
        }
        alertaRepository.deleteById(id);
    }

    /* ==========================
       Métodos auxiliares
       ========================== */

    private void mapearDtoParaEntity(AlertaCidadaoDTO dto, AlertaCidadaoEntity entity) {

        entity.setEndereco(dto.getEndereco());
        entity.setDescricao(dto.getDescricao());
        entity.setCoordLatitude(dto.getCoordLatitude());
        entity.setCoordLongitude(dto.getCoordLongitude());

        entity.setMunicipio(
                municipioRepository.findById(dto.getMunicipioId())
                        .orElseThrow(() -> new EntityNotFoundException("Município não encontrado"))
        );

        entity.setUsuario(
                usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"))
        );

        entity.setTipoNotificacao(
                tipoNotificacaoRepository.findById(dto.getTipoNotificacaoId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de notificação não encontrado"))
        );

        if (dto.getEspecieId() != null) {
            entity.setEspecie(
                    especieRepository.findById(dto.getEspecieId())
                            .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada"))
            );
        } else {
            entity.setEspecie(null);
        }
    }

    private AlertaCidadaoDTO mapearEntityParaDto(AlertaCidadaoEntity entity) {

        AlertaCidadaoDTO dto = new AlertaCidadaoDTO();
        dto.setId(entity.getId());
        dto.setEndereco(entity.getEndereco());
        dto.setDescricao(entity.getDescricao());
        dto.setData(entity.getData());
        dto.setCoordLatitude(entity.getCoordLatitude());
        dto.setCoordLongitude(entity.getCoordLongitude());

        dto.setMunicipioId(entity.getMunicipio().getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setTipoNotificacaoId(entity.getTipoNotificacao().getId());

        if (entity.getEspecie() != null) {
            dto.setEspecieId(entity.getEspecie().getId());
        }

        return dto;
    }

    public List<AlertaCidadaoDTO> buscarPorUsuario(Long usuarioId) {
    }
}
