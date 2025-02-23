package com.podcast.podcast.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SubscriptionDTO extends BaseDTO {

    private LocalDateTime subscribedStartDate;
    private LocalDateTime subscribedEndDate;
    @NotBlank
    private String subscriptionType;
    private Long userId; // Reference to the user
    private Long showId; // Reference to the show
}
