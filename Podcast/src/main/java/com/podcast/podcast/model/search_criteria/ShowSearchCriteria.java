package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSearchCriteria extends BaseSearchCriteria {
    private String title;
    private String description;
    private String coverImageUrl;
}
