package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FileDTO extends BaseDTO {
    private String name;
    private Long sizeInBytes;
    private String format;
    private String path;
    private Integer durationInSeconds;
    private Long episodeId; // Reference to the episode
}
