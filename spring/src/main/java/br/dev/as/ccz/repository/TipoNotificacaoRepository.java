package br.dev.as.ccz.repository;

import br.dev.as.ccz.domain.TipoNotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoNotificacaoRepository extends JpaRepository<TipoNotificacaoEntity, Long> {
}