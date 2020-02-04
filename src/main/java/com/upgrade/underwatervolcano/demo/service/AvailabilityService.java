package com.upgrade.underwatervolcano.demo.service;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    List<String> listOfDefaultAvailabilities();
    public List<String> listOfSearchAvailabilities(LocalDate startDate, LocalDate endDate);
}
