package com.upgrade.underwatervolcano.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrade.underwatervolcano.demo.database.entity.BookingEntity;
import com.upgrade.underwatervolcano.demo.database.repository.BookingsRepository;
import com.upgrade.underwatervolcano.demo.model.request.RequestBookingModel;
import com.upgrade.underwatervolcano.demo.model.request.RequestModifyBookingModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private BookingsRepository bookingsRepository;

    @Test
    public void shouldRetrieveAvailabilitiesDate() throws Exception {
        RequestBookingModel requestBookingModel = new RequestBookingModel();
        requestBookingModel.setFullName("John Doe");
        requestBookingModel.setEmail("test@test.com");
        requestBookingModel.setArrivalDate("2020-03-01");
        requestBookingModel.setDepartureDate("2020-03-03");

        this.mockMvc.perform(
                post("/create-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookingModel))
        ).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult = this.mockMvc.perform(
                get("/search-date-availabilities")
                        .param("startDate", "2020-02-29")
                        .param("endDate", "2020-03-04")
        ).andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        String[] responseArrays = response.substring(2, response.length() - 2).split("\",\"");
        assertEquals("2020-02-29", responseArrays[0]);
        assertEquals("2020-03-04", responseArrays[1]);
    }

    @Test
    public void shouldCreate_Modify_Delete_Booking() throws Exception {

        RequestModifyBookingModel requestBookingModel = new RequestModifyBookingModel();
        requestBookingModel.setFullName("John Doe");
        requestBookingModel.setEmail("test@test.com");
        requestBookingModel.setArrivalDate("2020-03-01");
        requestBookingModel.setDepartureDate("2020-03-03");

        MvcResult createMvcResult = this.mockMvc.perform(
                post("/create-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookingModel))
        ).andExpect(status().isOk()).andReturn();

        String uuid = createMvcResult.getResponse().getContentAsString();

        requestBookingModel.setUuid(uuid);
        requestBookingModel.setArrivalDate("2020-03-04");
        requestBookingModel.setDepartureDate("2020-03-05");

        this.mockMvc.perform(
                post("/modify-booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBookingModel))
        ).andExpect(status().isOk()).andReturn();

        BookingEntity bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        assertEquals(uuid, bookingEntity.getBookingUUID());
        assertEquals("test@test.com", bookingEntity.getEmail());
        assertEquals("John Doe", bookingEntity.getFullName());
        assertEquals(LocalDate.parse("2020-03-04"), bookingEntity.getArrivalDate());
        assertEquals(LocalDate.parse("2020-03-05"), bookingEntity.getDepartureDate());

        this.mockMvc.perform(
                post("/delete-booking")
                        .param("uuid", uuid)
        ).andExpect(status().isOk()).andReturn();

        bookingEntity = bookingsRepository.findBookingEntitiesByBookingUUID(uuid);

        assertNull(bookingEntity);
    }
}
