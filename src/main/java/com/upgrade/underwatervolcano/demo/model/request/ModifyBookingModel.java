package com.upgrade.underwatervolcano.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ModifyBookingModel extends RequestBookingModel {

    @NotBlank
    @JsonProperty("uuid")
    private String uuid;
}
