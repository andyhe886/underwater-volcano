package com.upgrade.underwatervolcano.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBookingModel {
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("fullName")
    private String fullName;

    @NotBlank
    @JsonProperty("arrivalDate")
    private String arrivalDate;

    @NotBlank
    @JsonProperty("departureDate")
    private String departureDate;
}
