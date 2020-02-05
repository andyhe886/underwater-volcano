package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;
import com.upgrade.underwatervolcano.demo.service.BookingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingsRepository bookingsRepository;

    public BookingServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    public String createBooking(BookingModel bookingModel) {

        LocalDate arrivalDate = bookingModel.getArrivalDate();
        LocalDate departureDate = bookingModel.getDepartureDate();
        LocalDate pastThreeDate = arrivalDate.minusDays(3);
        LocalDate futureThreeDate = departureDate.plusDays(3);

        if (bookingsRepository
                .existsBookingEntityByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(departureDate, arrivalDate)) {

            throw new IllegalStateException("It's already booked");
        }

        List<BookingEntity> bookingEntityIterable = bookingsRepository
                .findBookingEntitiesByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(futureThreeDate, pastThreeDate);

        boolean isEmpty = false;

        if (bookingEntityIterable.isEmpty()) {
            return save(bookingModel);
        } else {
            for (BookingEntity bookingEntity : bookingEntityIterable) {

                LocalDate bookedArrivalDate = bookingEntity.getArrivalDate();
                LocalDate bookedDepartureDate = bookingEntity.getDepartureDate();

                if (arrivalDate.isAfter(bookedDepartureDate) || departureDate.isBefore(bookedArrivalDate)) {
                    isEmpty = true;
                }
            }

            if(!isEmpty) {
                throw new IllegalStateException("It's already booked");
            }
        }

        return save(bookingModel);
    }

    public void modifyBooking(RequestModifyBookingModel requestModifyBookingModel) {
        String uuid = requestModifyBookingModel.getUuid();
        try {
            BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);
            bookingEntity.setFullName(requestModifyBookingModel.getFullName());
            bookingEntity.setEmail(requestModifyBookingModel.getEmail());
            bookingEntity.setArrivalDate(LocalDate.parse(requestModifyBookingModel.getArrivalDate()));
            bookingEntity.setDepartureDate(LocalDate.parse(requestModifyBookingModel.getDepartureDate()));
            bookingsRepository.save(bookingEntity);
        } catch (Exception e) {
            throw new IllegalStateException("Uh oh");
        }
    }

    @Transactional
    public void deleteBooking(String uuid) {
        try {
            bookingsRepository.deleteBookingEntityByBookingUUID(uuid);
        } catch (Exception e) {
            throw new IllegalStateException("Uh oh");
        }
    }

    private String save(BookingModel bookingModel) {
        String uuid = UUID.randomUUID().toString();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setFullName(bookingModel.getFullName());
        bookingEntity.setEmail(bookingModel.getEmail());
        bookingEntity.setArrivalDate(bookingModel.getArrivalDate());
        bookingEntity.setDepartureDate(bookingModel.getDepartureDate());
        bookingEntity.setBookingUUID(uuid);
        bookingsRepository.save(bookingEntity);

        bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);
        return bookingEntity.getBookingUUID();
    }
}
