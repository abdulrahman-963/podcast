package com.podcast.podcast.service.generic;

import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.service.mapper.GenericMapper;
import org.springframework.transaction.annotation.Transactional;

public interface CreattionService<E, D> {


    abstract BaseRepository<E, Long> getRepository();

    abstract GenericMapper<E, D> getMapper();

    @Transactional
    default D create(D entity) {
        return getMapper().toDto(getRepository().save(getMapper().toEntity(entity)));
    }
}
