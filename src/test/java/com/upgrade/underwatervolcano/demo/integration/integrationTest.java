package com.upgrade.underwatervolcano.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrade.underwatervolcano.demo.model.request.RequestBookingModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class integrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateBookings() throws Exception {

        RequestBookingModel requestBookingModel = new RequestBookingModel();
        requestBookingModel.setFullName("Andy He");
        requestBookingModel.setEmail("test@test.com");
        requestBookingModel.setArrivalDate("2020-03-01");
        requestBookingModel.setDepartureDate("2020-03-01");

        this.mockMvc.perform(
                post("/create-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookingModel))
        ).andExpect(status().isOk()).andReturn();
    }
}
