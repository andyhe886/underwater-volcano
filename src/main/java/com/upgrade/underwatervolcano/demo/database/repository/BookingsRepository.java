package com.upgrade.underwatervolcano.demo.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;

import java.time.LocalDate;
import java.util.List;

public interface BookingsRepository extends CrudRepository<BookingEntity, String> {

    public List<BookingEntity> findBookingEntitiesByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);
    public boolean existsBookingEntityByArrivalDateBetween(LocalDate departureDate, LocalDate arrivalDate);
}
