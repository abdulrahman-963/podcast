package com.podcast.podcast.controller.generic;

import com.podcast.podcast.model.dto.BaseDTO;
import com.podcast.podcast.model.dto.PageResponse;
import com.podcast.podcast.service.generic.ReadOnlyService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes"})
public interface ReadOnlyController<D extends BaseDTO, P> {

    abstract ReadOnlyService getService();

    @Operation(summary = "Get By Id", description = "Get related entity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity Found", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "204", description = "No data found", content = @Content)})
    @RateLimiter(name = "default")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<D> getById(@PathVariable Long id) {
        return ResponseEntity.ok((D) getService().getById(id));
    }

    @Operation(summary = "Get all available entities", description = "Get all entities details description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true, description = "Entity Found"),
            @ApiResponse(responseCode = "204", description = "No data found", content = @Content)})
    @RateLimiter(name = "default")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<PageResponse<D>> findAll(@PageableDefault(sort = {"id"}) Pageable pageable,
                                                    @RequestParam(required = false) Sort.Direction orderBy,
                                                    @RequestBody(required = false) @Valid P bsc) {
        Sort sort = Sort.by(Objects.nonNull(orderBy) ? orderBy : Sort.Direction.DESC, "id");
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return ResponseEntity.ok(getService().getAll(sortedPageable, bsc));
    }

}