package com.abhijits.movieticket.controller;

import com.abhijits.movieticket.dto.booking.BookingDto;
import com.abhijits.movieticket.dto.booking.BookingRequestDto;
import com.abhijits.movieticket.dto.transformer.BookingTransformer;
import com.abhijits.movieticket.service.BookingService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Validated
@RestController
@RequestMapping("/book")
public class BookingController {

    private BookingService bookingService;

    private BookingTransformer bookingTransformer;

    public BookingController(BookingService bookingService, BookingTransformer bookingTransformer) {
        this.bookingService = bookingService;
        this.bookingTransformer = bookingTransformer;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookingDto> post(@RequestBody BookingRequestDto bookingRequestDto) {
        BookingDto bookingDto = bookingTransformer.toDto(bookingService.book(bookingRequestDto));

        return ResponseEntity.created(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(BookingController.class)
                                         .get(bookingDto.getUuid())).toUri()
        ).body(bookingDto);
    }

    @PutMapping(
            path = "/{bookingUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookingDto> paymentSuccessful(@PathVariable UUID bookingUuid) {
        BookingDto bookingDto = bookingTransformer.toDto(bookingService.paymentSuccessful(bookingUuid));
        return ResponseEntity.accepted().body(bookingDto);
    }

    @GetMapping(
            path = "/{bookingUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookingDto> get(@PathVariable UUID bookingUuid) {
        return ResponseEntity.ok(bookingTransformer.toDto(bookingService.get(bookingUuid)));
    }

}

