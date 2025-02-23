package com.podcast.podcast.repository;

import com.podcast.podcast.model.entity.DeletedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedRecordRepository extends JpaRepository<DeletedRecord, Long> {
}
