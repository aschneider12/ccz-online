package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.AlertaCidadaoDTO;
import br.dev.as.ccz.api.dto.MarkerDTO;
import br.dev.as.ccz.domain.AlertaCidadaoEntity;
import br.dev.as.ccz.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaCidadaoService {

    private final AlertaCidadaoRepository alertaCidadaoRepository;
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

        this.alertaCidadaoRepository = alertaRepository;
        this.municipioRepository = municipioRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoNotificacaoRepository = tipoNotificacaoRepository;
        this.especieRepository = especieRepository;
    }

    @Transactional
    public AlertaCidadaoDTO salvarAlertaCidadao(AlertaCidadaoDTO dto) {

        AlertaCidadaoEntity entity = new AlertaCidadaoEntity();
        mapearDtoParaEntity(dto, entity);

        entity.setData(
                dto.getData() != null ? dto.getData() : LocalDateTime.now()
        );

        AlertaCidadaoEntity salvo = alertaCidadaoRepository.save(entity);
        return mapearEntityParaDto(salvo);
    }

    @Transactional(readOnly = true)
    public AlertaCidadaoDTO buscarPorId(Long id) {
        return alertaCidadaoRepository.findById(id)
                .map(this::mapearEntityParaDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Alerta cidadão não encontrado"));
    }



    @Transactional
    public AlertaCidadaoDTO atualizar(Long id, AlertaCidadaoDTO dto) {

        AlertaCidadaoEntity entity = alertaCidadaoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Alerta cidadão não encontrado"));

        mapearDtoParaEntity(dto, entity);

        return mapearEntityParaDto(alertaCidadaoRepository.save(entity));
    }

    @Transactional
    public void excluir(Long id) {
        if (!alertaCidadaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Alerta cidadão não encontrado");
        }
        alertaCidadaoRepository.deleteById(id);
    }

    /* ==========================
       Métodos auxiliares
       ========================== */

    private void mapearDtoParaEntity(AlertaCidadaoDTO dto, AlertaCidadaoEntity entity) {

        entity.setEndereco(dto.getEndereco());
        entity.setDescricao(dto.getDescricao());
        entity.setCoordLatitude(dto.getCoordLatitude());
        entity.setCoordLongitude(dto.getCoordLongitude());
        entity.setPoint(criarPointDoBancoPelasCoordenadas(dto));

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

        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setTipoNotificacaoId(entity.getTipoNotificacao().getId());

        if (entity.getEspecie() != null) {
            dto.setEspecieId(entity.getEspecie().getId());
        }

        return dto;
    }
    @Transactional(readOnly = true)
    public List<AlertaCidadaoDTO> buscarAlertas(Long usuarioId) {

        if(usuarioId != null && usuarioId != 0) {
            return alertaCidadaoRepository.findAllByUsuarioId(usuarioId)
                    .stream()
                    .map(this::mapearEntityParaDto)
                    .collect(Collectors.toList());

        } else {
            return alertaCidadaoRepository.findAll()
                    .stream()
                    .map(this::mapearEntityParaDto)
                    .collect(Collectors.toList());
        }
    }

    public List<MarkerDTO> buscarAlertasRegiao(Double latitude, Double longitude, Integer distancia) {

        return alertaCidadaoRepository.findAllAlertasRegiao(latitude, longitude, distancia);
    }

    private Point criarPointDoBancoPelasCoordenadas(AlertaCidadaoDTO createDTO) {

        var longitude = createDTO.getCoordLongitude();
        var latitude = createDTO.getCoordLatitude();

        GeometryFactory geometryFactory =
                new GeometryFactory(new PrecisionModel(), 4326);

        Point point = geometryFactory.createPoint(
                new Coordinate(longitude, latitude));

        return point;
    }

}
