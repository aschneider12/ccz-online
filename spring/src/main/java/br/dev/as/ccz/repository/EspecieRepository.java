package br.dev.as.ccz.repository;

import br.dev.as.ccz.domain.EspecieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepository extends JpaRepository<EspecieEntity, Long> {
}