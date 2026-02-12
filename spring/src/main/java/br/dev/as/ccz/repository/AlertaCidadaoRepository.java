package br.dev.as.ccz.repository;


import br.dev.as.ccz.api.dto.MarkerDTO;
import br.dev.as.ccz.domain.AlertaCidadaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaCidadaoRepository extends JpaRepository<AlertaCidadaoEntity, Long> {


    List<AlertaCidadaoEntity> findAllByUsuarioId(Long usuarioId);

    @Query(value = """
        SELECT 
            a.id,
            a.descricao,
            a.descricao,
            ST_Y(a.location_alert::geometry),
            ST_X(a.location_alert::geometry) 
        
        FROM alerta_cidadao a
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
}
