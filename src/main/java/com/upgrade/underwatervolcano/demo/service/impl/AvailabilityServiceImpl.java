package com.upgrade.underwatervolcano.demo.service.impl;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.service.AvailabilityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private BookingsRepository bookingsRepository;

    public AvailabilityServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    public List<String> listOfDefaultAvailabilities() {

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusMonths(1);

        List<LocalDate> listOfDatesBooked = searchListOfDatesBooked(currentDate, endDate);

        return giveListOfDatesAvailable(listOfDatesBooked , currentDate, endDate.plusDays(1));
    }

    public List<String> listOfSearchAvailabilities(LocalDate startDate, LocalDate endDate) {

        List<LocalDate> listOfDatesBooked = searchListOfDatesBooked(startDate, endDate);

        return giveListOfDatesAvailable(listOfDatesBooked , startDate, endDate.plusDays(1));
    }

    private List<LocalDate> searchListOfDatesBooked(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> listOfDatesBooked = new ArrayList<>();

        Iterable<BookingEntity> bookingEntityIterable = bookingsRepository
                .findBookingEntitiesByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(endDate, startDate);

        for (BookingEntity bookingEntity : bookingEntityIterable) {
            LocalDate arrivalDate = bookingEntity.getArrivalDate();
            LocalDate departureDate = bookingEntity.getDepartureDate();

            while (arrivalDate.isBefore(departureDate) || arrivalDate.isEqual(departureDate)) {
                listOfDatesBooked.add(arrivalDate);
                arrivalDate = arrivalDate.plusDays(1);
            }
        }

        return listOfDatesBooked;
    }

    private List<String> giveListOfDatesAvailable(List<LocalDate> listOfDatesBooked, LocalDate startDate, LocalDate endDate) {

        final long days = startDate.until(endDate, ChronoUnit.DAYS);

        return Stream.iterate(startDate, i -> i.plusDays(1))
                .limit(days)
                .filter(i -> !listOfDatesBooked.contains(i))
                .map(i -> i.toString())
                .collect(Collectors.toList());

    }
}
