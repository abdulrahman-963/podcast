package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.EpisodeDTO;
import com.podcast.podcast.model.search_criteria.EpisodeSearchCriteria;
import com.podcast.podcast.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/episodes")
@RequiredArgsConstructor
public class EpisodeController implements CreationController<EpisodeDTO>, ReadOnlyController<EpisodeDTO, EpisodeSearchCriteria>,
        DeletionController, ModificationController<EpisodeDTO> {

    private final EpisodeService episodeService;

    @Override
    public EpisodeService getService() {
        return this.episodeService;
    }
}
