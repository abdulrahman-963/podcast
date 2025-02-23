package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCriteria extends BaseSearchCriteria {
    private String username;
    private String email;
    private String role;
    private boolean isActive;
}
