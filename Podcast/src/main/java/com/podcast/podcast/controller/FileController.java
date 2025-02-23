package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.FileDTO;
import com.podcast.podcast.model.search_criteria.FileSearchCriteria;
import com.podcast.podcast.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/files")
@RequiredArgsConstructor
public class FileController implements CreationController<FileDTO>, ReadOnlyController<FileDTO, FileSearchCriteria>,
        DeletionController, ModificationController<FileDTO> {

    private final FileService fileService;

    @Override
    public FileService getService() {
        return this.fileService;
    }
}
