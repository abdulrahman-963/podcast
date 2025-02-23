package com.podcast.podcast.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CategoryDTO extends BaseDTO {

    @NotBlank
    private String name;
    private String description;
}
