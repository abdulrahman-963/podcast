package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EpisodeSearchCriteria extends BaseSearchCriteria {
    private String title;
    private String description;
    private LocalDateTime releaseDate;
    private Integer durationInSeconds;
}
