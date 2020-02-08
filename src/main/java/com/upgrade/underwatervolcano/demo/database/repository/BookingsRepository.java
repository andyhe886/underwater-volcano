package com.upgrade.underwatervolcano.demo.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<BookingEntity, String> {
    public List<BookingEntity> findBookingEntitiesByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);
    public boolean existsBookingEntityByDepartureDateLessThanEqualAndArrivalDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);

    public BookingEntity findBookingEntitiesByBookingUUID(String uuid);
    public void deleteBookingEntityByBookingUUID(String uuid);
}
