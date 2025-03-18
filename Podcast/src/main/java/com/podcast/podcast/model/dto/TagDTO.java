package com.podcast.podcast.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.podcast.podcast.enums.TagStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TagDTO extends BaseDTO {

    @NotBlank(message = "Tag name is required")
    private String name;

    private String colorCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalEpisodes = 0;

    private TagStatus status;

    private Boolean isVisible;
}
