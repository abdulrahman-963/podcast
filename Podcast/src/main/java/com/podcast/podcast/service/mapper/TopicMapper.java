package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.TopicDTO;
import com.podcast.podcast.model.entity.TopicEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper extends GenericMapper<TopicEntity, TopicDTO> {
}
