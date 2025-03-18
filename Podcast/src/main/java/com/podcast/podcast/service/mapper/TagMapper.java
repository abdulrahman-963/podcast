package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.TagDTO;
import com.podcast.podcast.model.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper extends GenericMapper<TagEntity, TagDTO> {
}
