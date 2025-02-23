package com.podcast.podcast.service.generic;

import com.podcast.podcast.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface DeletionService<E> {

    abstract BaseRepository<E, Long> getRepository();

    DeletionBackupService<E> getDeletionService();

    @Transactional
    default void delete(Long id) {

        E entity = getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with ID " + id + " not found."));

        getDeletionService().backupBeforeDelete(entity);
        getRepository().deleteById(id);

    }
}
