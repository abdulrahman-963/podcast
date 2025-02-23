package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.ShowDTO;
import com.podcast.podcast.model.search_criteria.ShowSearchCriteria;
import com.podcast.podcast.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/shows")
@RequiredArgsConstructor
public class ShowController implements CreationController<ShowDTO>, ReadOnlyController<ShowDTO, ShowSearchCriteria>,
        DeletionController, ModificationController<ShowDTO> {

    private final ShowService showService;

    @Override
    public ShowService getService() {
        return this.showService;
    }
}
