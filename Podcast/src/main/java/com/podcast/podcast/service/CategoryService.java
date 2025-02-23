package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.CategoryDTO;
import com.podcast.podcast.model.entity.CategoryEntity;
import com.podcast.podcast.model.search_criteria.CategorySearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.CategoryRepository;
import com.podcast.podcast.service.generic.*;
import com.podcast.podcast.service.mapper.CategoryMapper;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.specification.DynamicSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService
        implements CreattionService<CategoryEntity, CategoryDTO>,
        ReadOnlyService<CategoryEntity, CategoryDTO, CategorySearchCriteria>,
        ModificationService<CategoryEntity, CategoryDTO>, DeletionService<CategoryEntity, CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final DeletionBackupService<CategoryEntity> deletionBackupService;

    @Override
    public BaseRepository<CategoryEntity, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    public DeletionBackupService<CategoryEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<CategoryEntity, CategoryDTO> getMapper() {
        return categoryMapper;
    }

    @Override
    public DynamicSpecification<CategoryEntity> getSpecification(CategorySearchCriteria bsc) {

        log.debug("CategorySearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("name", bsc.getName());
            criteriaMap.put("description", bsc.getDescription());

        }

        return new DynamicSpecification<>(criteriaMap);
    }


}

