package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ShowDTO extends BaseDTO {
    private String title;
    private String description;
    private String coverImageUrl;
    private Long categoryId; // Reference to the category
    private Long channelId;  // Reference to the channel
}
