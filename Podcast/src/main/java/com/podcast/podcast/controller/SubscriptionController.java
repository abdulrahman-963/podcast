package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.SubscriptionDTO;
import com.podcast.podcast.model.search_criteria.SubscriptionSearchCriteria;
import com.podcast.podcast.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController implements CreationController<SubscriptionDTO>, ReadOnlyController<SubscriptionDTO, SubscriptionSearchCriteria>,
        DeletionController, ModificationController<SubscriptionDTO> {

    private final SubscriptionService subscriptionService;

    @Override
    public SubscriptionService getService() {
        return this.subscriptionService;
    }
}
