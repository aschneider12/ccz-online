package br.dev.as.ccz.api.mapper;

import br.dev.as.ccz.api.dto.TipoNotificacaoDTO;
import br.dev.as.ccz.domain.TipoNotificacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoNotificacaoMapper {
    TipoNotificacaoDTO toDTO(TipoNotificacaoEntity entity);
    TipoNotificacaoEntity toEntity(TipoNotificacaoDTO dto);
}