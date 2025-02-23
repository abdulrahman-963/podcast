package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.RatingEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends BaseRepository<RatingEntity, Long> {
}
