package com.podcast.podcast.controller.generic;

import com.podcast.podcast.model.dto.BaseDTO;
import com.podcast.podcast.service.generic.CreattionService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings({"unchecked", "rawtypes"})
public interface CreationController<D extends BaseDTO> {

    abstract CreattionService getService();

    @Operation(summary = "Create new entity", description = "This API is used to create new entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid entity details", content = @Content)})
    @RateLimiter(name = "default")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<D> create(@Valid @RequestBody @NotNull D request) {
        return ResponseEntity.ok((D) getService().create(request));
    }

}
