package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.SubscriptionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends BaseRepository<SubscriptionEntity, Long> {
}
