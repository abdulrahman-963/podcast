package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.SubscriptionDTO;
import com.podcast.podcast.model.entity.SubscriptionEntity;
import com.podcast.podcast.model.search_criteria.SubscriptionSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.SubscriptionRepository;
import com.podcast.podcast.service.generic.*;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.SubscriptionMapper;
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
public class SubscriptionService
        implements CreattionService<SubscriptionEntity, SubscriptionDTO>,
        ReadOnlyService<SubscriptionEntity, SubscriptionDTO, SubscriptionSearchCriteria>,
        ModificationService<SubscriptionEntity, SubscriptionDTO>, DeletionService<SubscriptionEntity, SubscriptionDTO> {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final DeletionBackupService<SubscriptionEntity> deletionBackupService;

    @Override
    public BaseRepository<SubscriptionEntity, Long> getRepository() {
        return subscriptionRepository;
    }

    @Override
    public DeletionBackupService<SubscriptionEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<SubscriptionEntity, SubscriptionDTO> getMapper() {
        return subscriptionMapper;
    }

    @Override
    public DynamicSpecification<SubscriptionEntity> getSpecification(SubscriptionSearchCriteria bsc) {
        log.debug("SubscriptionSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("subscribedStartDate", bsc.getSubscribedStartDate());
            criteriaMap.put("subscribedEndDate", bsc.getSubscribedEndDate());
            criteriaMap.put("subscriptionType", bsc.getSubscriptionType());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}

