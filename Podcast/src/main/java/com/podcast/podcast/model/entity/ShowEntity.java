package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_SHOW")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class ShowEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String coverImageUrl;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID", nullable = false)
    private ChannelEntity channel;
}
