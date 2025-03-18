package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.TopicEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends BaseRepository<TopicEntity, Long> {
}
