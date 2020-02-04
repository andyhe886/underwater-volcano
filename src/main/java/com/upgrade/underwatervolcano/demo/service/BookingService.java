package com.upgrade.underwatervolcano.demo.service;

import com.upgrade.underwatervolcano.demo.model.BookingModel;

public interface BookingService {
    public String createBooking(BookingModel bookingModel);
    public BookingModel retrieveBooking(String uuid);
    public void modifyBooking(BookingModel bookingModel);
    public void deleteBooking(BookingModel bookingModel);
}
