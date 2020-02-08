package com.upgrade.underwatervolcano.demo.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BOOKINGS")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Version
    @Column(name="VERSION")
    private Long version;

    @Column(name="ARRIVAL_DATE")
    private LocalDate arrivalDate;

    @Column(name="DEPARTURE_DATE")
    private LocalDate departureDate;

    @Column(name="EMAIL")
    private String email;

    @Column(name="FULL_NAME")
    private String fullName;

    @Column(name="BOOKING_UUID", unique = true)
    private String bookingUUID;

}
