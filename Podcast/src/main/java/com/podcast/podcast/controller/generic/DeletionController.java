package com.podcast.podcast.controller.generic;

import com.podcast.podcast.service.generic.DeletionService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SuppressWarnings({"rawtypes"})
public interface DeletionController {

    abstract DeletionService getService();

    @Operation(summary = "Delete entity details", description = "This API is used to delete existing entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleted")})
    @RateLimiter(name = "default")
    @DeleteMapping("/{id}")
    default ResponseEntity<String> delete(@PathVariable @NotNull Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

}