package com.podcast.podcast.model.search_criteria;

import com.podcast.podcast.enums.TagStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagSearchCriteria extends BaseSearchCriteria {

    private String name;
    private TagStatus status;
    private Boolean isVisible;
    private Boolean isFeatured;
    private Integer minEpisodes;
    private Integer maxEpisodes;
}