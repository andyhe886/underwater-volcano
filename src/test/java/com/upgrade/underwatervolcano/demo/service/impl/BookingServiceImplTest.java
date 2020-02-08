package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.DemoApplication;
import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.BookingModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@Execution(ExecutionMode.CONCURRENT)
public class BookingServiceImplTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private BookingsRepository bookingsRepository;


    private BookingModel bookingModel;


    @BeforeEach
    void setUp() {

        bookingModel = BookingModel.builder()
                .fullName("John Doe")
                .email("test@test.com")
                .arrivalDate(LocalDate.parse("2020-03-09"))
                .departureDate(LocalDate.parse("2020-03-09"))
                .build();

    }


    @Test
    void shouldCreateBooking() {

        String uuid = bookingService.createBooking(bookingModel);

        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        assertEquals(uuid, bookingEntity.getBookingUUID());
    }

    @Test
    void shouldCreateBooking_shouldThrowException() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(bookingModel);
        });
    }


}
