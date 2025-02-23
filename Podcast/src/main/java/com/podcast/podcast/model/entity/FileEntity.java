package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_FILE")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class FileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long sizeInBytes;

    private String format;

    private String path;

    private Integer durationInSeconds;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private EpisodeEntity episode;
}

