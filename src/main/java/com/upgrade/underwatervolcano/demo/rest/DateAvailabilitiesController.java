package com.upgrade.underwatervolcano.demo.rest;

import com.upgrade.underwatervolcano.demo.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrade.underwatervolcano.demo.util.DataValidatorUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DateAvailabilitiesController {

    private AvailabilityService availabilityService;

    public DateAvailabilitiesController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping(value = "/default-date-availabilities")
    public ResponseEntity<List<String>> defaultDateAvailabilities() {
        List<String> listDates = availabilityService.listOfDefaultAvailabilities();
        return ResponseEntity.status(HttpStatus.OK).body(listDates);
    }

    @GetMapping(value = "/search-date-availabilities")
    public ResponseEntity<List<String>> searchDateAvailabilities(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {

        DataValidatorUtil.retrieveValidation(startDate, endDate);

        List<String> listDates = availabilityService.listOfSearchAvailabilities(
                LocalDate.parse(startDate), LocalDate.parse(endDate)
        );

        return ResponseEntity.status(HttpStatus.OK).body(listDates);
    }
}
