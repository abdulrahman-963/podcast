package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "DELETED_RECORD")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class DeletedRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELETED_RECORD_GEN")
    @SequenceGenerator(name = "DELETED_RECORD_GEN", sequenceName = "DELETED_RECORD_SEQ", allocationSize = 1)
    private Long id;

    private String tableName;

    private String content; // Store as JSON string for simplicity

}

