package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.FileEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends BaseRepository<FileEntity, Long> {
}

