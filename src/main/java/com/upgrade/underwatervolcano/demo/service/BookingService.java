package com.upgrade.underwatervolcano.demo.service;

import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;

public interface BookingService {
    public String createBooking(BookingModel bookingModel);
    public void modifyBooking(RequestModifyBookingModel bookingModel);
    public void deleteBooking(String uuid);
}
