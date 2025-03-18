package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.TagDTO;
import com.podcast.podcast.model.search_criteria.TagSearchCriteria;
import com.podcast.podcast.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/tags")
@RequiredArgsConstructor
public class TagController implements CreationController<TagDTO>,
        DeletionController, ModificationController<TagDTO>, ReadOnlyController<TagDTO, TagSearchCriteria> {

    private final TagService tagService;

    @Override
    public TagService getService() {
        return this.tagService;
    }
}
