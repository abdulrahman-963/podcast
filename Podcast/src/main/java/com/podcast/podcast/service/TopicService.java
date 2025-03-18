package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.TopicDTO;
import com.podcast.podcast.model.entity.TopicEntity;
import com.podcast.podcast.model.search_criteria.TopicSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.TopicRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.TopicMapper;
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
public class TopicService implements CrudService<TopicEntity, TopicDTO, TopicSearchCriteria> {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final DeletionBackupService<TopicEntity> deletionBackupService;

    @Override
    public DeletionBackupService<TopicEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public BaseRepository<TopicEntity, Long> getRepository() {
        return topicRepository;
    }

    @Override
    public GenericMapper<TopicEntity, TopicDTO> getMapper() {
        return topicMapper;
    }

    @Override
    public DynamicSpecification<TopicEntity> getSpecification(TopicSearchCriteria bsc) {
        log.debug("TopicSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("name", bsc.getName());
            criteriaMap.put("level", bsc.getLevel());
            criteriaMap.put("status", bsc.getStatus());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}
