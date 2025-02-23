package com.podcast.podcast.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private String role;
    private boolean isActive;
}
