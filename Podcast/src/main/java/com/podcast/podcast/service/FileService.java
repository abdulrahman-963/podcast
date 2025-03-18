package com.podcast.podcast.service;

import com.podcast.podcast.model.dto.FileDTO;
import com.podcast.podcast.model.entity.FileEntity;
import com.podcast.podcast.model.search_criteria.FileSearchCriteria;
import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.repository.FileRepository;
import com.podcast.podcast.service.generic.CrudService;
import com.podcast.podcast.service.generic.DeletionBackupService;
import com.podcast.podcast.service.mapper.FileMapper;
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
public class FileService implements CrudService<FileEntity, FileDTO, FileSearchCriteria> {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final DeletionBackupService<FileEntity> deletionBackupService;

    @Override
    public BaseRepository<FileEntity, Long> getRepository() {
        return fileRepository;
    }

    @Override
    public DeletionBackupService<FileEntity> getDeletionService() {
        return deletionBackupService;
    }

    @Override
    public GenericMapper<FileEntity, FileDTO> getMapper() {
        return fileMapper;
    }

    @Override
    public DynamicSpecification<FileEntity> getSpecification(FileSearchCriteria bsc) {
        log.debug("FileSearchCriteria is: {}", bsc);
        Map<String, Object> criteriaMap = new HashMap<>();

        if (Objects.nonNull(bsc)) {
            criteriaMap.put("name", bsc.getName());
            criteriaMap.put("sizeInBytes", bsc.getSizeInBytes());
            criteriaMap.put("format", bsc.getFormat());
            criteriaMap.put("path", bsc.getPath());
            criteriaMap.put("durationInSeconds", bsc.getDurationInSeconds());
        }

        return new DynamicSpecification<>(criteriaMap);
    }
}

