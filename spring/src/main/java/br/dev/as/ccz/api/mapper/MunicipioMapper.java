package br.dev.as.ccz.api.mapper;

import br.dev.as.ccz.api.dto.MunicipioDTO;
import br.dev.as.ccz.domain.MunicipioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MunicipioMapper {
    MunicipioDTO toDTO(MunicipioEntity entity);
    MunicipioEntity toEntity(MunicipioDTO dto);

    // Útil para PUT: atualiza a entidade existente com os dados do DTO
    void updateEntityFromDto(MunicipioDTO dto, @MappingTarget MunicipioEntity entity);
}

