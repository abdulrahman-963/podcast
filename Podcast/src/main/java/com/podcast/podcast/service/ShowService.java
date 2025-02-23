package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.ShowDTO;
import com.podcast.podcast.model.entity.ShowEntity;
import com.podcast.podcast.model.search_criteria.ShowSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.ShowRepository;
import com.podcast.podcast.service.generic.*;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.ShowMapper;
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
public class ShowService
        implements CreattionService<ShowEntity, ShowDTO>,
        ReadOnlyService<ShowEntity, ShowDTO, ShowSearchCriteria>, ModificationService<ShowEntity, ShowDTO>,
        DeletionService<ShowEntity, ShowDTO> {

    private final ShowRepository showRepository;
    private final ShowMapper showMapper;
    private final DeletionBackupService<ShowEntity> deletionBackupService;

    @Override
    public BaseRepository<ShowEntity, Long> getRepository() {
        return showRepository;
    }

    @Override
    public DeletionBackupService<ShowEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<ShowEntity, ShowDTO> getMapper() {
        return showMapper;
    }

    @Override
    public DynamicSpecification<ShowEntity> getSpecification(ShowSearchCriteria bsc) {
        log.debug("ShowSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("title", bsc.getTitle());
            criteriaMap.put("description", bsc.getDescription());
            criteriaMap.put("coverImageUrl", bsc.getCoverImageUrl());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}
