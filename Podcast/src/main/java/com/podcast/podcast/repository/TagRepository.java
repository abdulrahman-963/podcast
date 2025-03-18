package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.TagEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<TagEntity, Long> {
}
