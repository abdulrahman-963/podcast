package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_EPISODE")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class EpisodeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime releaseDate = LocalDateTime.now();

    private Integer durationInSeconds;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private ShowEntity show;
}
