package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity, Long> {
}
