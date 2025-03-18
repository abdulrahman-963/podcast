package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.EpisodeDTO;
import com.podcast.podcast.model.entity.EpisodeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TopicMapper.class, TagMapper.class})
public interface EpisodeMapper extends GenericMapper<EpisodeEntity, EpisodeDTO> {

}