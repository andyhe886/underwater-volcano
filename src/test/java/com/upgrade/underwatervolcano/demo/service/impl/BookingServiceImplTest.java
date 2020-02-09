
package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.DemoApplication;
import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.BookingModel;

import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest(classes = DemoApplication.class)
@Execution(ExecutionMode.CONCURRENT)
public class BookingServiceImplTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private BookingsRepository bookingsRepository;

    private BookingModel bookingModel;

    private RequestModifyBookingModel requestModifyBookingModel;

    private static String uuid;

    @BeforeAll
    static void init() {
        uuid = "";
    }

    @BeforeEach
    void setUp() {

        bookingModel = BookingModel.builder()
                .fullName("John Doe")
                .email("test@test.com")
                .arrivalDate(LocalDate.parse("2020-03-09"))
                .departureDate(LocalDate.parse("2020-03-09"))
                .build();

        requestModifyBookingModel = new RequestModifyBookingModel();
        requestModifyBookingModel.setEmail("test@test.com");
        requestModifyBookingModel.setFullName("John Doe");
        requestModifyBookingModel.setArrivalDate("2020-03-10");
        requestModifyBookingModel.setDepartureDate("2020-03-10");


    }

    @Test
    void shouldCreateBooking() {

        uuid = bookingService.createBooking(bookingModel);

        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        assertEquals(uuid, bookingEntity.getBookingUUID());
    }

    @Test
    void shouldCreateBooking_shouldThrowException() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(50);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(bookingModel);
        });
    }

    @Test
    void modifyBooking() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        requestModifyBookingModel.setUuid(uuid);
        bookingService.modifyBooking(requestModifyBookingModel);

        TimeUnit.SECONDS.sleep(2);

        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        assertEquals("2020-03-10", bookingEntity.getDepartureDate().toString());
    }

    @Test
    void modifyBooking_shouldThrowException() throws InterruptedException {

        requestModifyBookingModel.setUuid(uuid);

        TimeUnit.SECONDS.sleep(1) ;
        TimeUnit.MILLISECONDS.sleep(50);

        requestModifyBookingModel.setUuid(uuid);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookingService.modifyBooking(requestModifyBookingModel);
        });

    }

    @Test
    void deleteBooking() throws InterruptedException {

        TimeUnit.SECONDS.sleep(4);
        bookingService.deleteBooking(uuid);
        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);
        assertNull(bookingEntity);
    }

    @Test
    void deleteBooking_shouldThrowException() throws InterruptedException {

        TimeUnit.SECONDS.sleep(4);
        TimeUnit.MILLISECONDS.sleep(50);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookingService.deleteBooking(uuid);
        });
    }
}
