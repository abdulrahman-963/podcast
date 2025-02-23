package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RatingDTO extends BaseDTO {
    private Float ratingValue; // Between 1.0 to 5.0
    private Long userId;       // Reference to the user
    private Long episodeId;    // Reference to the episode
}

