# underwater-volcano
## General Info

A web rest API to book campsite.

## Plugins that I use
| Plugin | README |
| ------ | ------ |
| Lombok | [https://github.com/rzwitserloot/lombok/blob/master/README]|


## Tools that I used

- Java 1.8
- Junit 5
- Spring boot 2.0
- Spring data jpa
- H2 http://localhost:8080/h2-console
- Swagger 2 http://localhost:8080/swagger-ui.html#
- IntelliJ
- Lombok

## Instructions

``` 
mvn clean install
mvn spring-boot:run
```

## Implementation 
### Rest endpoints

- GET /default-date-availabilities : It's use to retrieve current date up to 1 month
- GET /search-date-availabilities?endDate=?&startDate=? : It's use to retrieve start date to end date, somehow swagger switch the position, it should be startDate=?&endDate=?
- POST /create-booking : It's use to create booking
```
{
  "arrivalDate": "string",
  "departureDate": "string",
  "email": "string",
  "fullName": "string"
}
```
- POST /delete-booking?uuid=? : It's use to delete booking with the uuid from create booking endpoint
- POST /modify-booking : It's use to modify a booking
```
{
  "arrivalDate": "string",
  "departureDate": "string",
  "email": "string",
  "fullName": "string",
  "uuid": "string"
}
```

### How I solved the problems
How I solved the problems

For the GET /default-date-availabilities, you will be able to retrieve the current date for up to a month. While you are retrieving, it will search for the dates that are booked in the database. The following query is used, the departure date is less than or equal with the @query(endDate) AND the arrival date is greater than or equal with the @query(startDate). The endDate will be two days in the future and the startDate will be two days in the past. 

EX: |[X][X]|[X][O][X]|[X][X]|, with these conditions, it will be able to get all the bookings date than it will create a list of dates booked which will be used to create the list of dates available exclude with the list of dates booked.

For the GET /search-date-availabilities, the logic is the same as the /default-date-availabilities, but you can give a date range from the start date to the end date.

For the POST /create-booking, it will use synchronized block to prevent race conditions when multiple users are attempting to reserve the campsite.

There's a function (isSlotsEmpty()) to check if the slots are empty, then it will save the booking date(s) and return a booking UUID.
It will check the following conditions with the method (isSlotsEmpty()):

- Check if there are exactly the same booking dates exist.
- Retrieve all the bookings between (startDate - 2days) and (endDate + 2days) with the same query that we used in the /search-date-availabilities. 
If it's empty, the date(s) are available. 
If there are reserved date(s). it will check the if arrival date is after booked departure date or departure date is before booked arrival date. 

For the POST /modify-booking, it's using @Transactional, if multiple users attempts to modify the booking at the same time. it will save one of them, the other one will be rollback to his previous state.
It will also check if slots are empty before update.

For the POST /delete-booking, it's using @Transactional too same reason as /modify-booking to delete the booking.

### Constraints
All the checks, It's throws with IllegalArgumentException and IllegalStateException.

### Integration test
There's an integration test to test the flow (Search dates availabilities) and (Create, modify and delete).
There's another concurrent test to test the (create, modify and delete).

### [Lombok]
To be able to use Lombok locally in IntelliJ, you will need to install it.

 * Go to File > Settings > Plugins
 * Click on Browse repositories...
 * Search for Lombok Plugin
 * Click on Install plugin
 * Restart IntelliJ IDEA

 

   [Lombok]: <https://github.com/rzwitserloot/lombok>
