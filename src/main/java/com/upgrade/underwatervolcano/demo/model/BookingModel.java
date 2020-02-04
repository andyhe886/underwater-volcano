package com.upgrade.underwatervolcano.demo.model;

import lombok.Builder;
import java.time.LocalDate;


@Builder
public class BookingModel {

    private String email;

    private String fullName;

    private LocalDate arrivalDate;

    private LocalDate departureDate;

}
