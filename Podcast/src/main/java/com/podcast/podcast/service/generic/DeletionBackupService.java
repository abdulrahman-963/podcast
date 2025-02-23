package com.podcast.podcast.service.generic;

import com.podcast.podcast.model.entity.DeletedRecord;
import com.podcast.podcast.repository.DeletedRecordRepository;
import com.podcast.podcast.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeletionBackupService<E> {

    private final DeletedRecordRepository deletedRecordRepository;

    @Transactional
    public void backupBeforeDelete(E entity) {
        if (Objects.nonNull(entity)) {
            DeletedRecord deletedRecord = new DeletedRecord();
            deletedRecord.setTableName(entity.getClass().getSimpleName());
            deletedRecord.setContent(JsonUtil.toJson(null, entity));

            deletedRecordRepository.save(deletedRecord);
        }
    }
}
