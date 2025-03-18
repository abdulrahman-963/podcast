package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.EpisodeDTO;
import com.podcast.podcast.model.entity.*;
import com.podcast.podcast.model.search_criteria.EpisodeSearchCriteria;
import com.podcast.podcast.repository.*;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.EpisodeMapper;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.specification.DynamicSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EpisodeService implements CrudService<EpisodeEntity, EpisodeDTO, EpisodeSearchCriteria> {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final DeletionBackupService<EpisodeEntity> deletionBackupService;
    private final ShowRepository showRepository;
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;
    private final FileRepository fileRepository;

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
    @Transactional
    public EpisodeDTO create(EpisodeDTO episodeDTO) {
        // Validate foreign keys exist
        ShowEntity show = showRepository.findById(episodeDTO.getShowId())
                .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + episodeDTO.getShowId()));

        FileEntity file = fileRepository.findById(episodeDTO.getFileId())
                .orElseThrow(() -> new EntityNotFoundException("File not found with id: " + episodeDTO.getFileId()));

        EpisodeEntity episode = getMapper().toEntity(episodeDTO);
        episode.setShow(show);
        episode.setFile(file);

        if (!CollectionUtils.isEmpty(episodeDTO.getTopicIds())) {
            Set<TopicEntity> topics = episodeDTO.getTopicIds().stream()
                    .map(topicId -> topicRepository.findById(topicId)
                            .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + topicId)))
                    .collect(Collectors.toSet());
            episode.setTopics(topics);
        }

        // Handle tag associations
        if (!CollectionUtils.isEmpty(episodeDTO.getTagIds())) {
            Set<TagEntity> tags = episodeDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId)))
                    .collect(Collectors.toSet());
            episode.setTags(tags);
        }

        return getMapper().toDto(getRepository().save(episode));
    }

    @Override
    @Transactional
    public EpisodeDTO update(Long id, EpisodeDTO episodeDTO) {
        EpisodeEntity existingEpisode = episodeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with ID " + id + " not found."));


        episodeMapper.updateEntityFromDTO(episodeDTO, existingEpisode);


        // Validate foreign keys exist
        if (episodeDTO.getShowId() != null) {
            ShowEntity show = showRepository.findById(episodeDTO.getShowId())
                    .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + episodeDTO.getShowId()));

            existingEpisode.setShow(show);
        }

        // Handle topic associations if provided in the DTO
        if (episodeDTO.getTopicIds() != null) {
            Set<TopicEntity> topics = episodeDTO.getTopicIds().stream()
                    .map(topicId -> topicRepository.findById(topicId)
                            .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + topicId)))
                    .collect(Collectors.toSet());
            existingEpisode.setTopics(topics);
        }

        // Handle tag associations if provided in the DTO
        if (episodeDTO.getTagIds() != null) {
            Set<TagEntity> tags = episodeDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId)))
                    .collect(Collectors.toSet());
            existingEpisode.setTags(tags);
        }
        return getMapper().toDto(getRepository().save(existingEpisode));
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

    @Transactional
    public EpisodeDTO addTopicsToEpisode(Long episodeId, Set<Long> topicIds) {
        EpisodeEntity episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));

        Set<TopicEntity> topicsToAdd = topicIds.stream()
                .map(topicId -> topicRepository.findById(topicId)
                        .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + topicId)))
                .collect(Collectors.toSet());

        // Add topics (avoid duplicates)
        if (episode.getTopics() == null) {
            episode.setTopics(new HashSet<>());
        }
        episode.getTopics().addAll(topicsToAdd);

        return episodeMapper.toDto(episodeRepository.save(episode));
    }

    @Transactional
    public EpisodeDTO removeTopicsFromEpisode(Long episodeId, Set<Long> topicIds) {
        EpisodeEntity episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));

        if (episode.getTopics() != null) {
            episode.getTopics().removeIf(topic -> topicIds.contains(topic.getId()));
        }

        return episodeMapper.toDto(episodeRepository.save(episode));
    }

    @Transactional
    public EpisodeDTO addTagsToEpisode(Long episodeId, Set<Long> tagIds) {
        EpisodeEntity episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));

        Set<TagEntity> tagsToAdd = tagIds.stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId)))
                .collect(Collectors.toSet());

        // Add tags (avoid duplicates)
        if (episode.getTags() == null) {
            episode.setTags(new HashSet<>());
        }
        episode.getTags().addAll(tagsToAdd);

        return episodeMapper.toDto(episodeRepository.save(episode));
    }


    @Transactional
    public EpisodeDTO removeTagsFromEpisode(Long episodeId, Set<Long> tagIds) {
        EpisodeEntity episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));

        if (episode.getTags() != null) {
            episode.getTags().removeIf(tag -> tagIds.contains(tag.getId()));
        }

        return episodeMapper.toDto(episodeRepository.save(episode));
    }


}
