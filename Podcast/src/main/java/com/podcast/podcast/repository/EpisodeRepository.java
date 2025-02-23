package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.EpisodeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends BaseRepository<EpisodeEntity, Long> {
}

