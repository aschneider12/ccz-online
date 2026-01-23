package br.dev.as.ccz.api.mapper;

import br.dev.as.ccz.api.dto.EspecieDTO;
import br.dev.as.ccz.domain.EspecieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EspecieMapper {
    EspecieDTO toDTO(EspecieEntity entity);
    EspecieEntity toEntity(EspecieDTO dto);
}