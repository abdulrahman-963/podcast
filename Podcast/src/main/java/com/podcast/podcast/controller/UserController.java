package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.UserDTO;
import com.podcast.podcast.model.search_criteria.UserSearchCriteria;
import com.podcast.podcast.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URI_V1 + "/users")
@RequiredArgsConstructor
public class UserController implements CreationController<UserDTO>, ReadOnlyController<UserDTO, UserSearchCriteria>,
        DeletionController, ModificationController<UserDTO> {

    private final UserService userService;

    @Override
    public UserService getService() {
        return this.userService;
    }
}
