package com.podcast.podcast.controller.generic;

import com.podcast.podcast.model.dto.BaseDTO;
import com.podcast.podcast.service.generic.ModificationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings({"unchecked", "rawtypes"})
public interface ModificationController<D extends BaseDTO> {

    ModificationService getService();

    @Operation(summary = "Update entity details", description = "This API is used to update existing entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid entity details", content = @Content)})
    @RateLimiter(name = "default")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<D> update(@PathVariable Long id, @Valid @RequestBody @NotNull D request) {
        return ResponseEntity.ok((D) getService().update(id, request));
    }

}