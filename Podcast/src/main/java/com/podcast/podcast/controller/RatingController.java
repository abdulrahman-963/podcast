package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.RatingDTO;
import com.podcast.podcast.model.search_criteria.RatingSearchCriteria;
import com.podcast.podcast.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/ratings")
@RequiredArgsConstructor
public class RatingController implements CreationController<RatingDTO>, ReadOnlyController<RatingDTO, RatingSearchCriteria>,
        DeletionController, ModificationController<RatingDTO> {

    private final RatingService ratingService;

    @Override
    public RatingService getService() {
        return this.ratingService;
    }
}
