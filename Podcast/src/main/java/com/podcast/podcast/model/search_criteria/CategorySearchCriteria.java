package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategorySearchCriteria extends BaseSearchCriteria {
    private String name;
    private String description;
}
