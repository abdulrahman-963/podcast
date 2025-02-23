package com.podcast.podcast.model.search_criteria;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionSearchCriteria extends BaseSearchCriteria {
    private LocalDateTime subscribedStartDate;
    private LocalDateTime subscribedEndDate;
    private String subscriptionType;
}
