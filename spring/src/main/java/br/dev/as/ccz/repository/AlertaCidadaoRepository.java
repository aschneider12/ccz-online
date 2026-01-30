package br.dev.as.ccz.repository;


import br.dev.as.ccz.domain.AlertaCidadaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaCidadaoRepository extends JpaRepository<AlertaCidadaoEntity, Long> {

}
