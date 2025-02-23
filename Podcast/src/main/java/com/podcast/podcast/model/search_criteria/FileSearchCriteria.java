package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileSearchCriteria extends BaseSearchCriteria {
    private String name;
    private Long sizeInBytes;
    private String format;
    private String path;
    private Integer durationInSeconds;
}
