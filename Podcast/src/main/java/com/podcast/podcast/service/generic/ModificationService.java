package com.podcast.podcast.service.generic;

import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.service.mapper.GenericMapper;
import jakarta.persistence.EntityNotFoundException;

public interface ModificationService<E, D> {

    abstract BaseRepository<E, Long> getRepository();

    abstract GenericMapper<E, D> getMapper();

    default D update(Long id, D updatedEntity) {
        getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with ID " + id + " not found."));
        return getMapper().toDto(getRepository().save(getMapper().toEntity(updatedEntity)));

    }
}
