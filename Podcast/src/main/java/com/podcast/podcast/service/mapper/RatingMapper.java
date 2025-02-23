package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.RatingDTO;
import com.podcast.podcast.model.entity.RatingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper extends GenericMapper<RatingEntity, RatingDTO> {
}