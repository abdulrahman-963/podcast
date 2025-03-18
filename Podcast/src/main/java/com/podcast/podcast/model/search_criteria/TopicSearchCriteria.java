package com.podcast.podcast.model.search_criteria;

import com.podcast.podcast.enums.TopicStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicSearchCriteria extends BaseSearchCriteria {

    private String name;
    private Integer level;
    private Long parentTopicId;
    private TopicStatus status;
    private Boolean isVisible;
    private Boolean isFeatured;
    private Integer minEpisodes;
    private Integer maxEpisodes;
}