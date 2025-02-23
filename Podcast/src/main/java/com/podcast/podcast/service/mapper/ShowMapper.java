package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.ShowDTO;
import com.podcast.podcast.model.entity.ShowEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowMapper extends GenericMapper<ShowEntity, ShowDTO> {
}
