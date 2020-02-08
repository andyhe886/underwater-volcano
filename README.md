# underwater-volcano
## General Info

A web application to book campsite.

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

## Instructions

``` 
mvn clean install
mvn spring-boot:run
```

## Strategy 
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


### [Lombok]
To be able to use Lombok locally in IntelliJ, you will need to install it.

 * Go to File > Settings > Plugins
 * Click on Browse repositories...
 * Search for Lombok Plugin
 * Click on Install plugin
 * Restart IntelliJ IDEA

 

   [Lombok]: <https://github.com/rzwitserloot/lombok>
