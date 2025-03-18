package com.podcast.podcast.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.podcast.podcast.enums.TopicStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TopicDTO extends BaseDTO {

    @NotBlank(message = "Topic name is required")
    private String name;

    private String description;

    @NotNull(message = "Level is required")
    private Integer level;

    private Long parentTopicId;

    private String path;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalEpisodes = 0;

    private TopicStatus status;

    private Boolean isVisible;
}
