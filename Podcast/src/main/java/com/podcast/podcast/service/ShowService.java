package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.ShowDTO;
import com.podcast.podcast.model.entity.CategoryEntity;
import com.podcast.podcast.model.entity.ChannelEntity;
import com.podcast.podcast.model.entity.ShowEntity;
import com.podcast.podcast.model.search_criteria.ShowSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.CategoryRepository;
import com.podcast.podcast.repository.ChannelRepository;
import com.podcast.podcast.repository.ShowRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.service.mapper.ShowMapper;
import com.podcast.podcast.specification.DynamicSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowService implements CrudService<ShowEntity, ShowDTO, ShowSearchCriteria> {

    private final ShowRepository showRepository;
    private final ShowMapper showMapper;
    private final DeletionBackupService<ShowEntity> deletionBackupService;

    private final CategoryRepository categoryRepository;
    private final ChannelRepository channelRepository;

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
    public ShowDTO create(ShowDTO showDTO) {
        // Validate foreign keys exist
        CategoryEntity category = categoryRepository.findById(showDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + showDTO.getCategoryId()));

        ChannelEntity channel = channelRepository.findById(showDTO.getChannelId())
                .orElseThrow(() -> new EntityNotFoundException("Channel not found with id: " + showDTO.getChannelId()));

        ShowEntity entity = getMapper().toEntity(showDTO);
        entity.setCategory(category);
        entity.setChannel(channel);

        return getMapper().toDto(getRepository().save(entity));
    }

    @Override
    public ShowDTO update(Long id, ShowDTO showDTO) {
        showRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with ID " + id + " not found."));

        // Validate foreign keys exist
        CategoryEntity category = categoryRepository.findById(showDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + showDTO.getCategoryId()));

        ChannelEntity channel = channelRepository.findById(showDTO.getChannelId())
                .orElseThrow(() -> new EntityNotFoundException("Channel not found with id: " + showDTO.getChannelId()));

        ShowEntity entity = getMapper().toEntity(showDTO);
        entity.setId(id);
        entity.setCategory(category);
        entity.setChannel(channel);

        return getMapper().toDto(getRepository().save(entity));
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
