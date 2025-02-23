package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class EpisodeDTO extends BaseDTO {
    private String title;
    private String description;
    private LocalDateTime releaseDate;
    private Integer durationInSeconds;
    private Long showId; // Reference to the show
}
