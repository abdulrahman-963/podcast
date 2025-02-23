package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

}
