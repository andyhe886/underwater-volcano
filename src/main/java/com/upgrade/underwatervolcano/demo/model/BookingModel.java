package com.upgrade.underwatervolcano.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookingModel {

    private String email;

    private String fullName;

    private LocalDate arrivalDate;

    private LocalDate departureDate;

}
