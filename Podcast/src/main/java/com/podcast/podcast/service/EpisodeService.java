package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.EpisodeDTO;
import com.podcast.podcast.model.entity.EpisodeEntity;
import com.podcast.podcast.model.search_criteria.EpisodeSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.EpisodeRepository;
import com.podcast.podcast.service.generic.*;
import com.podcast.podcast.service.mapper.EpisodeMapper;
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
public class EpisodeService
        implements CreattionService<EpisodeEntity, EpisodeDTO>,
        ReadOnlyService<EpisodeEntity, EpisodeDTO, EpisodeSearchCriteria>,
        ModificationService<EpisodeEntity, EpisodeDTO>, DeletionService<EpisodeEntity, EpisodeDTO> {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final DeletionBackupService<EpisodeEntity> deletionBackupService;


    @Override
    public BaseRepository<EpisodeEntity, Long> getRepository() {
        return episodeRepository;
    }

    @Override
    public DeletionBackupService<EpisodeEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<EpisodeEntity, EpisodeDTO> getMapper() {
        return episodeMapper;
    }

    @Override
    public DynamicSpecification<EpisodeEntity> getSpecification(EpisodeSearchCriteria bsc) {

        log.debug("EpisodeSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("title", bsc.getTitle());
            criteriaMap.put("description", bsc.getDescription());
            criteriaMap.put("releaseDate", bsc.getReleaseDate());
            criteriaMap.put("durationInSeconds", bsc.getDurationInSeconds());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}
