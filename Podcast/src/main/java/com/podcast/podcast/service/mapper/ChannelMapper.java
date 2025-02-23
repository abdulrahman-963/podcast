package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.ChannelDTO;
import com.podcast.podcast.model.entity.ChannelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper extends GenericMapper<ChannelEntity, ChannelDTO> {
}
