package br.dev.as.ccz.repository;

import br.dev.as.ccz.api.dto.MarkerDTO;
import br.dev.as.ccz.domain.AlertaCczEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaCczRepository extends JpaRepository<AlertaCczEntity, Long> {

    @Query(value = """
        SELECT  
            a.id,
            t.descricao,
            a.descricao,
            ST_Y(a.location_alert::geometry),
            ST_X(a.location_alert::geometry) 
        FROM alerta_cidadao a 
        LEFT JOIN tipo_notificacao t ON t.id = a.tipo_notificacao_id 
        WHERE ST_DWithin(
            a.location_alert,
            ST_SetSRID(
                ST_MakePoint(:longitude, :latitude),
                4326
            )::geography, :distancia
        )
    """, nativeQuery = true
    )
    List<MarkerDTO> findAllAlertasRegiao(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("distancia") Integer distancia
    );

    /**
     * Busca alertas por município
     */
    Page<AlertaCczEntity> findByMunicipioId(Long municipioId, Pageable pageable);

    /**
     * Busca alertas por tipo de notificação
     */
    Page<AlertaCczEntity> findByTipoNotificacaoId(Long tipoNotificacaoId, Pageable pageable);

    /**
     * Busca alertas por espécie
     */
    Page<AlertaCczEntity> findByEspecieId(Long especieId, Pageable pageable);

    /**
     * Busca alertas por usuário
     */
    Page<AlertaCczEntity> findByUsuarioId(Long usuarioId, Pageable pageable);

    /**
     * Busca alertas dentro de um período
     */
    @Query("SELECT a FROM AlertaCczEntity a WHERE a.data BETWEEN :dataInicio AND :dataFim")
    Page<AlertaCczEntity> findByDataBetween(
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    /**
     * Busca alertas por município e período
     */
    @Query("SELECT a FROM AlertaCczEntity a WHERE a.municipio.id = :municipioId " +
            "AND a.data BETWEEN :dataInicio AND :dataFim")
    Page<AlertaCczEntity> findByMunicipioAndDataBetween(
            @Param("municipioId") Long municipioId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    /**
     * Busca alertas por tipo de notificação e urgência mínima
     */
    @Query("SELECT a FROM AlertaCczEntity a WHERE a.tipoNotificacao.urgencia >= :urgenciaMinima")
    Page<AlertaCczEntity> findByUrgenciaMinima(
            @Param("urgenciaMinima") Long urgenciaMinima,
            Pageable pageable
    );

    /**
     * Busca alertas recentes (últimos N dias)
     */
    @Query("SELECT a FROM AlertaCczEntity a WHERE a.data >= :dataLimite ORDER BY a.data DESC")
    List<AlertaCczEntity> findAlertasRecentes(@Param("dataLimite") LocalDateTime dataLimite);

    /**
     * Conta alertas por município
     */
    Long countByMunicipioId(Long municipioId);

    /**
     * Conta alertas por tipo de notificação
     */
    Long countByTipoNotificacaoId(Long tipoNotificacaoId);

    /**
     * Verifica se existe alerta com a mesma descrição (para validar unicidade)
     */
    boolean existsByDescricao(String descricao);

    /**
     * Verifica se existe alerta com a mesma descrição, exceto o ID especificado
     */
    boolean existsByDescricaoAndIdNot(String descricao, Long id);
}