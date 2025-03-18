package com.podcast.podcast.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EpisodeDTO extends BaseDTO {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private LocalDateTime releaseDate;

    @NotNull
    private Integer durationInSeconds;

    @NotNull
    private Long showId; // Reference to the show

    @NotNull
    private Long fileId; // Reference to the file

    private Set<Long> topicIds = new HashSet<>();

    private Set<Long> tagIds = new HashSet<>();
}
