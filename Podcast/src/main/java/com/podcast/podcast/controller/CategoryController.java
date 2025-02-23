package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.CategoryDTO;
import com.podcast.podcast.model.search_criteria.CategorySearchCriteria;
import com.podcast.podcast.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/categories")
@RequiredArgsConstructor
public class CategoryController implements CreationController<CategoryDTO>, ReadOnlyController<CategoryDTO, CategorySearchCriteria>,
        DeletionController, ModificationController<CategoryDTO> {

    private final CategoryService categoryService;

    @Override
    public CategoryService getService() {
        return this.categoryService;
    }
}
