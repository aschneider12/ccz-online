package br.dev.as.ccz.repository;

import br.dev.as.ccz.domain.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    /**
     * Busca um usuário pelo CPF
     * @param cpf CPF do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<UsuarioEntity> findByCpf(String cpf);

    /**
     * Verifica se existe um usuário com o CPF informado
     * @param cpf CPF a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um usuário com o CPF informado, exceto o usuário com o ID especificado
     * Útil para validação em atualizações
     * @param cpf CPF a ser verificado
     * @param id ID do usuário a ser excluído da verificação
     * @return true se existir outro usuário com o mesmo CPF, false caso contrário
     */
    boolean existsByCpfAndIdNot(String cpf, Long id);
}