package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_SUBSCRIPTION")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class SubscriptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime subscribedStartDate;

    private LocalDateTime subscribedEndDate;

    @Column(nullable = false)
    private String subscriptionType = "Free";

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}

