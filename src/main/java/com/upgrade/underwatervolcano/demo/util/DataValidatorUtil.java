package com.upgrade.underwatervolcano.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DataValidatorUtil {

    private DataValidatorUtil() {

    }


    public static void bookingValidation(String startDateString, String endDateString) {
        try {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            checkIfValidDates(startDate, endDate);
            checkDatesBookingConstraint(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The date must be formatted: YYYY-MM-DD");
        }

    }
    public static void retrieveValidation(String startDateString, String endDateString) {

        try {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            checkIfValidDates(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The date must be formatted: YYYY-MM-DD");
        }
    }

    private static void checkIfValidDates(LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("departure date must be after arrival date");
        }
    }

    public static void checkDatesBookingConstraint(LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (3 < days) {
            throw new IllegalArgumentException("3 days maximum");
        } else if(LocalDate.now().isEqual(startDate)) {
            throw new IllegalArgumentException("You must book 1 day(s) ahead of current day");
        }
    }

}
