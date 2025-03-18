package com.podcast.podcast.controller;

import com.podcast.podcast.config.Constants;
import com.podcast.podcast.controller.generic.CreationController;
import com.podcast.podcast.controller.generic.DeletionController;
import com.podcast.podcast.controller.generic.ModificationController;
import com.podcast.podcast.controller.generic.ReadOnlyController;
import com.podcast.podcast.model.dto.EpisodeDTO;
import com.podcast.podcast.model.search_criteria.EpisodeSearchCriteria;
import com.podcast.podcast.service.EpisodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @Operation(summary = "Add topics to episode", description = "Associate topics with an existing episode")
    @PostMapping("/{id}/topics")
    public ResponseEntity<EpisodeDTO> addTopicsToEpisode(
            @PathVariable Long id,
            @RequestBody Set<Long> topicIds) {

        return ResponseEntity.ok(episodeService.addTopicsToEpisode(id, topicIds));
    }

    @Operation(summary = "Remove topics from episode", description = "Remove topic associations from an episode")
    @DeleteMapping("/{id}/topics")
    public ResponseEntity<EpisodeDTO> removeTopicsFromEpisode(
            @PathVariable Long id,
            @RequestBody Set<Long> topicIds) {

        return ResponseEntity.ok(episodeService.removeTopicsFromEpisode(id, topicIds));
    }

    @Operation(summary = "Add tags to episode", description = "Associate tags with an existing episode")
    @PostMapping("/{id}/tags")
    public ResponseEntity<EpisodeDTO> addTagsToEpisode(
            @PathVariable Long id,
            @RequestBody Set<Long> tagIds) {

        return ResponseEntity.ok(episodeService.addTagsToEpisode(id, tagIds));
    }

    @Operation(summary = "Remove tags from episode", description = "Remove tag associations from an episode")
    @DeleteMapping("/{id}/tags")
    public ResponseEntity<EpisodeDTO> removeTagsFromEpisode(
            @PathVariable Long id,
            @RequestBody Set<Long> tagIds) {

        return ResponseEntity.ok(episodeService.removeTagsFromEpisode(id, tagIds));
    }

}
