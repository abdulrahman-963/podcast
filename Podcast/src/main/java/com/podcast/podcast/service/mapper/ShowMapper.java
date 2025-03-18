package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.ShowDTO;
import com.podcast.podcast.model.entity.ShowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowMapper extends GenericMapper<ShowEntity, ShowDTO> {

    @Override
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "channel.name", target = "channelName")
    ShowDTO toDto(ShowEntity entity);
}
