package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.FileDTO;
import com.podcast.podcast.model.entity.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper extends GenericMapper<FileEntity, FileDTO> {
}
