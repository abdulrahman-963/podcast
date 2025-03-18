package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.EpisodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends BaseRepository<EpisodeEntity, Long> {

    @Query("SELECT DISTINCT e FROM EpisodeEntity e " +
            "JOIN e.topics t " +
            "WHERE t.id IN :topicIds")
    Page<EpisodeEntity> findByTopicIds(@Param("topicIds") List<Long> topicIds, Pageable pageable);

    @Query("SELECT DISTINCT e FROM EpisodeEntity e " +
            "JOIN e.tags t " +
            "WHERE t.id IN :tagIds")
    Page<EpisodeEntity> findByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);

}

