package com.podcast.podcast.model.entity;

import com.podcast.podcast.enums.TopicStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;

@Entity
@Table(name = DB_TABLE_PREFIX + "_TOPIC")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class TopicEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOPIC_GEN")
    @SequenceGenerator(name = "TOPIC_GEN", sequenceName = "TOPIC_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "LEVELS", nullable = false)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TOPIC_ID")
    private TopicEntity parentTopic;

    @Column(name = "PATH")
    private String path;

    private Integer totalEpisodes = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicStatus status = TopicStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean isVisible = true;

    @OneToMany(mappedBy = "parentTopic", cascade = CascadeType.ALL)
    private Set<TopicEntity> childTopics = new HashSet<>();

    @ManyToMany(mappedBy = "topics")
    private Set<EpisodeEntity> episodes = new HashSet<>();

    // Helper method to add child topic
    public void addChildTopic(TopicEntity child) {
        childTopics.add(child);
        child.setParentTopic(this);
    }

    // Helper method to update path when parent changes
    @PrePersist
    @PreUpdate
    private void updatePath() {
        if (parentTopic == null) {
            this.path = "/" + this.name;
        } else {
            this.path = parentTopic.getPath() + "/" + this.name;
        }
    }
}
