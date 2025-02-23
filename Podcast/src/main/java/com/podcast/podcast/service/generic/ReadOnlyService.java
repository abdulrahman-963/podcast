package com.podcast.podcast.service.generic;

import com.podcast.podcast.repository.BaseRepository;
import com.podcast.podcast.service.mapper.GenericMapper;
import com.podcast.podcast.specification.DynamicSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ReadOnlyService<E, D, P> {

    abstract BaseRepository<E, Long> getRepository();

    abstract GenericMapper<E, D> getMapper();

    abstract DynamicSpecification<E> getSpecification(P bsc);

    default D getById(Long id) {
        return getMapper().toDto(getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity with ID " + id + " not found.")
        ));
    }

    default Page<D> getAll(Pageable pageable, P bsc) {
        Page<E> page = getRepository().findAll(getSpecification(bsc), pageable);
        return new PageImpl(getMapper().toDtoList(page.getContent()),
                pageable, page.getTotalElements());
    }


}
