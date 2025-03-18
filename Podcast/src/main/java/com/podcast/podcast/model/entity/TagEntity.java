package com.podcast.podcast.model.entity;

import com.podcast.podcast.enums.TagStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;

@Entity
@Table(name = DB_TABLE_PREFIX + "_TAG")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_GEN")
    @SequenceGenerator(name = "TAG_GEN", sequenceName = "TAG_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String colorCode;

    private Integer totalEpisodes = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagStatus status = TagStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean isVisible = true;

    @ManyToMany(mappedBy = "tags")
    private Set<EpisodeEntity> episodes = new HashSet<>();

}
