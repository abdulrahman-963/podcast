package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.ShowEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends BaseRepository<ShowEntity, Long> {
}

