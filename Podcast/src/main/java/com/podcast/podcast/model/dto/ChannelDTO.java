package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ChannelDTO extends BaseDTO {
    private String name;
    private String description;
    private String coverImageUrl;
    private Long userId; // Reference to the user who owns the channel
}
