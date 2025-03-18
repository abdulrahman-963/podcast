package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    private FileEntity file;


    @ManyToMany
    @JoinTable(
            name = "episode_topic",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<TopicEntity> topics = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "episode_tag",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags = new HashSet<>();

    // Helper methods to manage relationships

    public void addTopic(TopicEntity topic) {
        topics.add(topic);
        topic.getEpisodes().add(this);

        // Update episode count
        topic.setTotalEpisodes(topic.getTotalEpisodes() + 1);

        // Update parent topics
        TopicEntity parent = topic.getParentTopic();
        while (parent != null) {
            parent.setTotalEpisodes(parent.getTotalEpisodes() + 1);
            parent = parent.getParentTopic();
        }
    }

    public void removeTopic(TopicEntity topic) {
        topics.remove(topic);
        topic.getEpisodes().remove(this);

        // Update episode count
        if (topic.getTotalEpisodes() > 0) {
            topic.setTotalEpisodes(topic.getTotalEpisodes() - 1);
        }

        // Update parent topics
        TopicEntity parent = topic.getParentTopic();
        while (parent != null) {
            if (parent.getTotalEpisodes() > 0) {
                parent.setTotalEpisodes(parent.getTotalEpisodes() - 1);
            }
            parent = parent.getParentTopic();
        }
    }

    public void addTag(TagEntity tag) {
        tags.add(tag);
        tag.getEpisodes().add(this);

        // Update episode count
        tag.setTotalEpisodes(tag.getTotalEpisodes() + 1);
    }

    public void removeTag(TagEntity tag) {
        tags.remove(tag);
        tag.getEpisodes().remove(this);

        // Update episode count
        if (tag.getTotalEpisodes() > 0) {
            tag.setTotalEpisodes(tag.getTotalEpisodes() - 1);
        }
    }

}
