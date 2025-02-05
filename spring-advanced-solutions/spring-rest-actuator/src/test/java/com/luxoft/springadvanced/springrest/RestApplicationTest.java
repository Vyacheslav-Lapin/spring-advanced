package com.luxoft.springadvanced.springrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.springadvanced.springrest.beans.FlightBuilder;
import com.luxoft.springadvanced.springrest.exceptions.PassengerNotFoundException;
import com.luxoft.springadvanced.springrest.model.Country;
import com.luxoft.springadvanced.springrest.dao.CountryRepository;
import com.luxoft.springadvanced.springrest.model.Flight;
import com.luxoft.springadvanced.springrest.model.Passenger;
import com.luxoft.springadvanced.springrest.dao.PassengerRepository;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.val;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FlightBuilder.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RestApplicationTest {

    MockMvc mvc;

    Flight flight;

    Map<String, Country> countriesMap;

    @MockitoBean
    @NonFinal PassengerRepository passengerRepository;

    @MockitoBean
    @NonFinal CountryRepository countryRepository;

    @Test
    void testGetAllCountries() throws Exception {
        when(countryRepository.findAll()).thenReturn(new ArrayList<>(countriesMap.values()));
        mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPassengers() throws Exception {
        when(passengerRepository.findAll()).thenReturn(new ArrayList<>(flight.getPassengers()));

        mvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));

        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    void testPassengerNotFound() {
        val throwable = assertThrows(ServletException.class,
                                     () -> mvc.perform(get("/passengers/30"))
                                                    .andExpect(status().isNotFound()));

        assertEquals(PassengerNotFoundException.class, throwable.getCause().getClass());
    }

    @Test
    @Disabled
    void testPostPassenger() throws Exception {

        val passenger = new Passenger("Peter Michelsen")
            .setCountry(countriesMap.get("US"));

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        mvc.perform(post("/passengers")
                .content(new ObjectMapper().writeValueAsString(passenger))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Peter Michelsen")))
                .andExpect(jsonPath("$.country.codeName", is("US")))
                .andExpect(jsonPath("$.country.name", is("USA")))
                .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

        verify(passengerRepository, times(1)).save(passenger);

    }

    @Test
    void testPatchPassenger() throws Exception {
        val passenger = new Passenger("Sophia Graham")
            .setCountry(countriesMap.get("UK"))
            .setRegistered(false);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        @Language("JSON") val updates = "{\"name\":\"Sophia Jones\", \"country\":\"AU\", \"isRegistered\":\"true\"}";

        mvc.perform(patch("/passengers/{id}", 1)
                .content(updates)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerRepository, times(1)).save(passenger);
    }

}
