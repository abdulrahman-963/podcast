package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.ChannelDTO;
import com.podcast.podcast.model.entity.ChannelEntity;
import com.podcast.podcast.model.search_criteria.ChannelSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.ChannelRepository;
import com.podcast.podcast.service.generic.*;
import com.podcast.podcast.service.mapper.ChannelMapper;
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
public class ChannelService
        implements CreattionService<ChannelEntity, ChannelDTO>,
        ReadOnlyService<ChannelEntity, ChannelDTO, ChannelSearchCriteria>,
        ModificationService<ChannelEntity, ChannelDTO>, DeletionService<ChannelEntity, ChannelDTO> {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final DeletionBackupService<ChannelEntity> deletionBackupService;

    @Override
    public BaseRepository<ChannelEntity, Long> getRepository() {
        return channelRepository;
    }

    @Override
    public DeletionBackupService<ChannelEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<ChannelEntity, ChannelDTO> getMapper() {
        return channelMapper;
    }

    @Override
    public DynamicSpecification<ChannelEntity> getSpecification(ChannelSearchCriteria bsc) {
        log.debug("ChannelSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("name", bsc.getName());
            criteriaMap.put("description", bsc.getDescription());
            criteriaMap.put("coverImageUrl", bsc.getCoverImageUrl());
        }

        return new DynamicSpecification<>(criteriaMap);
    }


}
