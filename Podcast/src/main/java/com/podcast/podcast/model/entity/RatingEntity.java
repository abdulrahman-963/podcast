package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_RATING")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class RatingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float ratingValue; // 1.0 to 5.0

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private EpisodeEntity episode;
}
