package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.TagDTO;
import com.podcast.podcast.model.entity.TagEntity;
import com.podcast.podcast.model.search_criteria.TagSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.TagRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.TagMapper;
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
public class TagService implements CrudService<TagEntity, TagDTO, TagSearchCriteria> {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final DeletionBackupService<TagEntity> deletionBackupService;

    @Override
    public DeletionBackupService<TagEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public BaseRepository<TagEntity, Long> getRepository() {
        return tagRepository;
    }

    @Override
    public GenericMapper<TagEntity, TagDTO> getMapper() {
        return tagMapper;
    }

    @Override
    public DynamicSpecification<TagEntity> getSpecification(TagSearchCriteria bsc) {
        log.debug("TagSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("name", bsc.getName());
            criteriaMap.put("status", bsc.getStatus());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}
