package br.dev.as.ccz.repository;

import br.dev.as.ccz.domain.MunicipioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioEntity, Long> {
    // Aqui você pode adicionar métodos de busca customizados se precisar, ex:
    // List<MunicipioEntity> findByUf(String uf);
}
