package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelSearchCriteria extends BaseSearchCriteria {
    private String name;
    private String description;
    private String coverImageUrl;
}
