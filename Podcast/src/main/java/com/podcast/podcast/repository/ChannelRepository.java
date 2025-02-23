package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.ChannelEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends BaseRepository<ChannelEntity, Long> {
}
