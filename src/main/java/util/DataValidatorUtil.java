package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DataValidatorUtil {

    private DataValidatorUtil() {
    }

    public static void validation(String startDateString, String endDateString) {

        try {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            checkIfValidDates(startDate, endDate);
        } catch (DateTimeParseException e) {
            System.err.println("Date time parse exception" + e);
        } catch (IllegalArgumentException e) {
            System.err.println("Errors in the sequence"+ e);
        }
    }

    private static void checkIfValidDates(LocalDate startDate, LocalDate endDate) {
        if(endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("EndDate must be after StartDate");
        }
    }

}
