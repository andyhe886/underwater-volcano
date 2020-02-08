package com.upgrade.underwatervolcano.demo.rest;

import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestBookingModel;
import com.upgrade.underwatervolcano.demo.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrade.underwatervolcano.demo.util.DataValidatorUtil;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class BookingManagementController {

    private BookingService bookingService;

    public BookingManagementController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/create-booking")
    public ResponseEntity<String> createBooking(@Valid @RequestBody RequestBookingModel requestBookingModel) {

        DataValidatorUtil.bookingValidation(requestBookingModel.getArrivalDate(), requestBookingModel.getDepartureDate());

        String fullName = requestBookingModel.getFullName();
        String email = requestBookingModel.getEmail();
        String arrivalDate = requestBookingModel.getArrivalDate();
        String departureDate = requestBookingModel.getDepartureDate();

        BookingModel bookingModel = BookingModel.builder()
                .fullName(fullName)
                .email(email)
                .arrivalDate(LocalDate.parse(arrivalDate))
                .departureDate(LocalDate.parse(departureDate)).build();

        String uuid = bookingService.createBooking(bookingModel);

        return ResponseEntity.status(HttpStatus.OK).body(uuid);
    }

    @PostMapping(value = "/modify-booking")
    public ResponseEntity<String> modifyBooking(@Valid @RequestBody RequestModifyBookingModel modifyBookingModel) {

        DataValidatorUtil.bookingValidation(modifyBookingModel.getArrivalDate(), modifyBookingModel.getDepartureDate());

        bookingService.modifyBooking(modifyBookingModel);
        return ResponseEntity.status(HttpStatus.OK).body("Booking has been modified");

    }

    @PostMapping(value = "/delete-booking")
    public ResponseEntity<String> deleteBooking(@Valid @RequestParam String uuid) {
        bookingService.deleteBooking(uuid);
        return ResponseEntity.status(HttpStatus.OK).body("Booking has been deleted");

    }
}
