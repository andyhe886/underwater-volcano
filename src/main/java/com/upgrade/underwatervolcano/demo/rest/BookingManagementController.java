package com.upgrade.underwatervolcano.demo.rest;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.model.request.ModifyBookingModel;
import com.upgrade.underwatervolcano.demo.model.response.ResponseBookingModel;
import com.upgrade.underwatervolcano.demo.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BookingManagementController {
    private static final Logger logger = LoggerFactory.getLogger(BookingManagementController.class);

    private BookingService bookingService;

    public BookingManagementController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/create-booking")
    public ResponseEntity<String> createBooking(@Valid @RequestBody ResponseBookingModel responseBookingModel) {


        BookingModel bookingModel = BookingModel.builder().build();

        bookingService.createBooking(bookingModel);
        return ResponseEntity.status(HttpStatus.OK).body("test");

    }

    @GetMapping(value = "/retrieve-booking")
    public ResponseEntity<BookingEntity> retrieveBooking(@RequestParam(value = "uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping(value = "/modify-booking")
    public ResponseEntity<String> modifyBooking(@Valid @RequestBody ModifyBookingModel bookingModel) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping(value = "/delete-booking")
    public ResponseEntity<String> deleteBooking(@Valid @RequestParam String uuid) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
