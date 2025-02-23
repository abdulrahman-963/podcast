package com.podcast.podcast.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static com.podcast.podcast.config.Constants.DB_TABLE_PREFIX;


@Entity
@Table(name = DB_TABLE_PREFIX + "_USER")
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role = "USER";

    @Column(nullable = false)
    private boolean isActive = true;
}
