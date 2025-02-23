package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.UserDTO;
import com.podcast.podcast.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserEntity, UserDTO> {
}
