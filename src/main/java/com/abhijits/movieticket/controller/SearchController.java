package com.abhijits.movieticket.controller;

import com.abhijits.movieticket.dto.theatre.TheatreDto;
import com.abhijits.movieticket.service.SearchService;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Validated
@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Set<TheatreDto>> get(
            @NotNull(message = "cityUuid should not be null") @RequestParam UUID cityUuid,
            @NotNull(message = "movieUuid should not be null") @RequestParam UUID movieUuid,
            @NotNull(message = "date should not be null") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime date
    ) {
        return ResponseEntity.ok(searchService.search(cityUuid, movieUuid, date));
    }

}


// curl -v -X GET -H 'Accept: application/json' "http://localhost:8080/search?movieUuid=aeb9e216-16a1-4697-b4c0-0cd0c2343ae1&cityUuid=9a14287b-e145-494f-9364-f03584410aae&date=2023-01-09T09:00:00Z"