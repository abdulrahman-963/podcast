package com.podcast.podcast.service.mapper;

import com.podcast.podcast.model.dto.CategoryDTO;
import com.podcast.podcast.model.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryEntity, CategoryDTO> {
}
