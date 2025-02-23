package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingSearchCriteria extends BaseSearchCriteria {
    private Float ratingValue; // Between 1.0 to 5.0
}

