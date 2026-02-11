package br.dev.as.ccz.service;

import br.dev.as.ccz.api.dto.AlertaCczCreateDTO;
import br.dev.as.ccz.api.dto.AlertaCczDTO;
import br.dev.as.ccz.api.dto.AlertaCczUpdateDTO;
import br.dev.as.ccz.api.dto.MarkerDTO;
import br.dev.as.ccz.api.mapper.AlertaCczMapper;
import br.dev.as.ccz.domain.*;
import br.dev.as.ccz.repository.*;
import jakarta.validation.ValidationException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaCczService {

    private final AlertaCczRepository alertaCczRepository;
    private final MunicipioRepository municipioRepository;
    private final TipoNotificacaoRepository tipoNotificacaoRepository;
    private final EspecieRepository especieRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlertaCczMapper mapper;

    public AlertaCczService(
            AlertaCczRepository alertaCczRepository,
            MunicipioRepository municipioRepository,
            TipoNotificacaoRepository tipoNotificacaoRepository,
            EspecieRepository especieRepository,
            UsuarioRepository usuarioRepository,
            AlertaCczMapper mapper) {
        this.alertaCczRepository = alertaCczRepository;
        this.municipioRepository = municipioRepository;
        this.tipoNotificacaoRepository = tipoNotificacaoRepository;
        this.especieRepository = especieRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todos os alertas do ccz, se tiver filtro aplique
     */
    @Transactional(readOnly = true)
    public List<AlertaCczDTO> buscarAlertasCcz(Long usuarioId) {

        //TODO - outros filtros que possam surgir, tratar como object filtro
        if (usuarioId != null && usuarioId != 0) {
            return alertaCczRepository.findAllByUsuarioId(usuarioId)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());

        } else {
            return alertaCczRepository.findAll()
                    .stream()
                    .map(mapper::toDTO).toList();
        }
    }

    /**
     * Busca um alerta por ID individual
     */
    @Transactional(readOnly = true)
    public AlertaCczDTO buscarPorId(Long id) {
        AlertaCczEntity alerta = alertaCczRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Alerta não encontrado com ID: " + id));
        return mapper.toDTO(alerta);
    }

    /**
     * Busca alertas por município
     */
    @Transactional(readOnly = true)
    public Page<AlertaCczDTO> buscarPorMunicipio(Long municipioId, Pageable pageable) {
        return alertaCczRepository.findByMunicipioId(municipioId, pageable)
                .map(mapper::toDTO);
    }

    /**
     * Busca alertas por tipo de notificação
     */
    @Transactional(readOnly = true)
    public Page<AlertaCczDTO> buscarPorTipoNotificacao(Long tipoNotificacaoId, Pageable pageable) {
        return alertaCczRepository.findByTipoNotificacaoId(tipoNotificacaoId, pageable)
                .map(mapper::toDTO);
    }

    /**
     * Busca alertas dentro de um período
     */
    @Transactional(readOnly = true)
    public Page<AlertaCczDTO> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        return alertaCczRepository.findByDataBetween(dataInicio, dataFim, pageable)
                .map(mapper::toDTO);
    }

    /**
     * Busca alertas recentes (últimos 7 dias)
     */
    @Transactional(readOnly = true)
    public List<AlertaCczDTO> buscarAlertasRecentes() {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(7);
        return alertaCczRepository.findAlertasRecentes(dataLimite).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo alerta
     */
    @Transactional
    public AlertaCczDTO savarAlerta(AlertaCczCreateDTO createDTO) {

        // Verifica se a descrição já existe
        if (alertaCczRepository.existsByDescricao(createDTO.getDescricao())) {
            throw new ValidationException("Já existe um alerta com esta descrição");
        }

        // Busca e valida o município
        MunicipioEntity municipio = municipioRepository.findById(createDTO.getMunicipioId())
                .orElseThrow(() -> new ValidationException("Município não encontrado"));

        // Busca e valida o tipo de notificação
        TipoNotificacaoEntity tipoNotificacao = tipoNotificacaoRepository.findById(createDTO.getTipoNotificacaoId())
                .orElseThrow(() -> new ValidationException("Tipo de notificação não encontrado"));

        // Busca e valida o usuário
        UsuarioEntity usuario = usuarioRepository.findById(createDTO.getUsuarioId())
                .orElseThrow(() -> new ValidationException("Usuário não encontrado"));

        // Converte DTO para Entity
        AlertaCczEntity alerta = mapper.toEntity(createDTO);

        // Seta os relacionamentos
        alerta.setMunicipio(municipio);
        alerta.setTipoNotificacao(tipoNotificacao);
        alerta.setUsuario(usuario);
        alerta.setPoint( criarPointDoBancoPelasCoordenadas(createDTO) );

        // Busca espécie se informada
        if (createDTO.getEspecieId() != null) {
            EspecieEntity especie = especieRepository.findById(createDTO.getEspecieId())
                    .orElseThrow(() -> new ValidationException("Espécie não encontrada"));
            alerta.setEspecie(especie);
        }

        // Salva o alerta
        AlertaCczEntity alertaSalvo = alertaCczRepository.save(alerta);
        return mapper.toDTO(alertaSalvo);
    }

    private Point criarPointDoBancoPelasCoordenadas(AlertaCczCreateDTO createDTO) {

        var longitude = createDTO.getCoordLongitude();
        var latitude = createDTO.getCoordLatitude();

        GeometryFactory geometryFactory =
                new GeometryFactory(new PrecisionModel(), 4326);

        Point point = geometryFactory.createPoint(
                new Coordinate(longitude, latitude));

        return point;
    }

    /**
     * Atualiza um alerta existente
     */
    @Transactional
    public AlertaCczDTO atualizar(Long id, AlertaCczUpdateDTO updateDTO) {
        AlertaCczEntity alerta = alertaCczRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Alerta não encontrado com ID: " + id));

        // Verifica se a descrição já existe em outro registro
        if (updateDTO.getDescricao() != null &&
                alertaCczRepository.existsByDescricaoAndIdNot(updateDTO.getDescricao(), id)) {
            throw new ValidationException("Já existe um alerta com esta descrição");
        }

        // Atualiza os campos básicos
        mapper.updateEntityFromDTO(updateDTO, alerta);

        // Atualiza município se informado
        if (updateDTO.getMunicipioId() != null) {
            MunicipioEntity municipio = municipioRepository.findById(updateDTO.getMunicipioId())
                    .orElseThrow(() -> new ValidationException("Município não encontrado"));
            alerta.setMunicipio(municipio);
        }

        // Atualiza tipo de notificação se informado
        if (updateDTO.getTipoNotificacaoId() != null) {
            TipoNotificacaoEntity tipoNotificacao = tipoNotificacaoRepository.findById(updateDTO.getTipoNotificacaoId())
                    .orElseThrow(() -> new ValidationException("Tipo de notificação não encontrado"));
            alerta.setTipoNotificacao(tipoNotificacao);
        }

        // Atualiza espécie se informada
        if (updateDTO.getEspecieId() != null) {
            EspecieEntity especie = especieRepository.findById(updateDTO.getEspecieId())
                    .orElseThrow(() -> new ValidationException("Espécie não encontrada"));
            alerta.setEspecie(especie);
        }

        AlertaCczEntity alertaAtualizado = alertaCczRepository.save(alerta);
        return mapper.toDTO(alertaAtualizado);
    }

    /**
     * Exclui um alerta
     */
    @Transactional
    public void excluir(Long id) {
        if (!alertaCczRepository.existsById(id)) {
            throw new ValidationException("Alerta não encontrado com ID: " + id);
        }
        alertaCczRepository.deleteById(id);
    }

    /**
     * Conta alertas por município
     */
    @Transactional(readOnly = true)
    public Long contarPorMunicipio(Long municipioId) {
        return alertaCczRepository.countByMunicipioId(municipioId);
    }

    /**
     * Conta alertas por tipo de notificação
     */
    @Transactional(readOnly = true)
    public Long contarPorTipoNotificacao(Long tipoNotificacaoId) {
        return alertaCczRepository.countByTipoNotificacaoId(tipoNotificacaoId);
    }

    public List<MarkerDTO> buscarAlertasRegiao(Double latitude, Double longitude, Integer distancia) {
        return alertaCczRepository.findAllAlertasRegiao(latitude, longitude, distancia);
    }
}
