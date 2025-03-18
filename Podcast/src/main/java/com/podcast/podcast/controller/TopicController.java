package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.TopicDTO;
import com.podcast.podcast.model.search_criteria.TopicSearchCriteria;
import com.podcast.podcast.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/topics")
@RequiredArgsConstructor
public class TopicController implements CreationController<TopicDTO>,
        DeletionController, ModificationController<TopicDTO>, ReadOnlyController<TopicDTO, TopicSearchCriteria> {

    private final TopicService topicService;

    @Override
    public TopicService getService() {
        return this.topicService;
    }
}
