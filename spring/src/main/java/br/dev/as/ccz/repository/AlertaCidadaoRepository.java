package br.dev.as.ccz.repository;


import br.dev.as.ccz.domain.AlertaCidadaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaCidadaoRepository extends JpaRepository<AlertaCidadaoEntity, Long> {


    List<AlertaCidadaoEntity> findAllByUsuarioId(Long usuarioId);
}
