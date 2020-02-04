package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingsRepository bookingsRepository;

    public BookingServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public String createBooking(BookingModel bookingModel) {
        synchronized (this) {
//            Iterable<BookingEntity> bookingEntityIterable = bookingsRepository.findAll();
//            for (BookingEntity bookingEntity : bookingEntityIterable) {
//                bookingsRepository.f
//            }
        }
        return null;
    }

    @Override
    public BookingModel retrieveBooking(String uuid) {

        return null;
    }

    @Override
    public void modifyBooking(BookingModel bookingModel) {
        synchronized (this) {

        }

    }

    @Override
    public void deleteBooking(BookingModel bookingModel) {
        synchronized (this) {

        }
    }
}
