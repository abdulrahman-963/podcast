package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.RatingDTO;
import com.podcast.podcast.model.entity.RatingEntity;
import com.podcast.podcast.model.search_criteria.RatingSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.RatingRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.RatingMapper;
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
public class RatingService implements CrudService<RatingEntity, RatingDTO, RatingSearchCriteria> {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final DeletionBackupService<RatingEntity> deletionBackupService;

    @Override
    public BaseRepository<RatingEntity, Long> getRepository() {
        return ratingRepository;
    }

    @Override
    public DeletionBackupService<RatingEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<RatingEntity, RatingDTO> getMapper() {
        return ratingMapper;
    }

    @Override
    public DynamicSpecification<RatingEntity> getSpecification(RatingSearchCriteria bsc) {
        log.debug("RatingSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("ratingValue", bsc.getRatingValue());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}
