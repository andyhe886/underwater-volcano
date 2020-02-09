package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.BookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;
import com.upgrade.underwatervolcano.demo.service.BookingService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
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

        String uuid = "";

        synchronized (this) {
            try {
                boolean isSlotsEmpty = isSlotsEmpty(arrivalDate, departureDate);

                if (isSlotsEmpty) {
                    uuid = save(bookingModel);
                } else {
                    throw new IllegalArgumentException("It's already booked, please choose another date(s)");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

        }

        return uuid;
    }

    public void modifyBooking(RequestModifyBookingModel requestModifyBookingModel) {
        try {
            LocalDate arrivalDate = LocalDate.parse(requestModifyBookingModel.getArrivalDate());
            LocalDate departureDate = LocalDate.parse(requestModifyBookingModel.getDepartureDate());

            boolean isSlotsEmpty = isSlotsEmpty(arrivalDate, departureDate);

            if (isSlotsEmpty) {
                update(requestModifyBookingModel);
            } else {
                throw new IllegalArgumentException("It's already booked, please choose another date(s)");
            }
        } catch (DataAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deleteBooking(String uuid) {
        try {
            delete(uuid);
        } catch (DataAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isSlotsEmpty(LocalDate arrivalDate, LocalDate departureDate) {
        LocalDate pastTwoDate = arrivalDate.minusDays(2);
        LocalDate futureTwoDate = departureDate.plusDays(2);

        if (bookingsRepository
                .existsBookingEntityByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(departureDate, arrivalDate)) {
            return false;
        }

        List<BookingEntity> bookingEntityIterable = bookingsRepository
                .findBookingEntitiesByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(futureTwoDate, pastTwoDate);

        if (bookingEntityIterable.isEmpty()) {
            return true;
        } else {

            HashSet<Boolean> isSlotsEmpty = new HashSet<>();

            for (BookingEntity bookingEntity : bookingEntityIterable) {

                LocalDate bookedArrivalDate = bookingEntity.getArrivalDate();
                LocalDate bookedDepartureDate = bookingEntity.getDepartureDate();

                isSlotsEmpty.add(arrivalDate.isAfter(bookedDepartureDate) || departureDate.isBefore(bookedArrivalDate));
            }

            return !isSlotsEmpty.contains(false);
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

    @Transactional
    void update(RequestModifyBookingModel requestModifyBookingModel) {

        String uuid = requestModifyBookingModel.getUuid();
        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        if (bookingEntity != null) {
            bookingEntity.setFullName(requestModifyBookingModel.getFullName());
            bookingEntity.setEmail(requestModifyBookingModel.getEmail());
            bookingEntity.setArrivalDate(LocalDate.parse(requestModifyBookingModel.getArrivalDate()));
            bookingEntity.setDepartureDate(LocalDate.parse(requestModifyBookingModel.getDepartureDate()));
            bookingsRepository.save(bookingEntity);
        } else {
            throw new IllegalArgumentException("The booking do not exist");
        }

    }

    @Transactional
    void delete(String uuid) {
        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        if (bookingEntity != null) {
            bookingsRepository.delete(bookingEntity);
        } else {
            throw new IllegalArgumentException("The booking do not exist");
        }
    }
}
