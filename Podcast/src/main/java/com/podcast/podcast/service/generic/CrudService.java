package com.podcast.podcast.service.generic;

import com.podcast.podcast.model.dto.BaseDTO;
import com.podcast.podcast.model.entity.BaseEntity;
import com.podcast.podcast.model.search_criteria.BaseSearchCriteria;

public interface CrudService<E extends BaseEntity, D extends BaseDTO, P extends BaseSearchCriteria> extends
        ReadOnlyService<E, D, P>,
        CreattionService<E, D>,
        ModificationService<E, D>,
        DeletionService<E> {
}
