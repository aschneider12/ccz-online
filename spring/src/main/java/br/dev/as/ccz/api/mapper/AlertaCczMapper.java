package br.dev.as.ccz.api.mapper;

import br.dev.as.ccz.api.dto.AlertaCczCreateDTO;
import br.dev.as.ccz.api.dto.AlertaCczDTO;
import br.dev.as.ccz.api.dto.AlertaCczUpdateDTO;
import br.dev.as.ccz.domain.AlertaCczEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlertaCczMapper {

    /**
     * Converte AlertaCczEntity para AlertaCczDTO
     */
    @Mapping(source = "tipoNotificacao.id", target = "tipoNotificacaoId")
    @Mapping(source = "especie.id", target = "especieId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    AlertaCczDTO toDTO(AlertaCczEntity entity);

    /**
     * Converte AlertaCczCreateDTO para AlertaCczEntity
     * Ignora os relacionamentos pois serão setados manualmente no service
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoNotificacao", ignore = true)
    @Mapping(target = "especie", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    AlertaCczEntity toEntity(AlertaCczCreateDTO dto);

    /**
     * Atualiza uma entidade existente com dados do DTO
     * Ignora campos nulos no DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoNotificacao", ignore = true)
    @Mapping(target = "especie", ignore = true)
    @Mapping(target = "usuario", ignore = true )
    void updateEntityFromDTO(AlertaCczUpdateDTO dto, @MappingTarget AlertaCczEntity entity);
//
//    // Mapeamentos para entidades relacionadas
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "uf", target = "uf")
//    MunicipioSimpleDTO toMunicipioSimpleDTO(MunicipioEntity entity);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "urgencia", target = "urgencia")
//    TipoNotificacaoSimpleDTO toTipoNotificacaoSimpleDTO(TipoNotificacaoEntity entity);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "descricao", target = "descricao")
//    EspecieSimpleDTO toEspecieSimpleDTO(EspecieEntity entity);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "nome", target = "nome")
//    @Mapping(source = "cpf", target = "cpf")
//    UsuarioSimpleDTO toUsuarioSimpleDTO(UsuarioEntity entity);
//}{
}
