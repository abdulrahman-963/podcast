package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.ChannelDTO;
import com.podcast.podcast.model.search_criteria.ChannelSearchCriteria;
import com.podcast.podcast.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/channels")
@RequiredArgsConstructor
public class ChannelController implements CreationController<ChannelDTO>, ReadOnlyController<ChannelDTO, ChannelSearchCriteria>,
        DeletionController, ModificationController<ChannelDTO> {

    private final ChannelService channelService;

    @Override
    public ChannelService getService() {
        return this.channelService;
    }
}
