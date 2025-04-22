package ru.ibs.training.java.spring.data.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ibs.training.java.spring.data.rest.beans.BeansBuilder;
import ru.ibs.training.java.spring.data.rest.dao.CountryRepository;
import ru.ibs.training.java.spring.data.rest.dao.PersonRepository;
import ru.ibs.training.java.spring.data.rest.exceptions.PersonNotFoundException;
import ru.ibs.training.java.spring.data.rest.model.Country;
import ru.ibs.training.java.spring.data.rest.model.Person;
import ru.ibs.training.java.spring.data.rest.model.Room;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(BeansBuilder.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestApplicationTest {

    MockMvc mvc;

    Room room;

    Map<String, Country> countriesMap;

    @MockitoBean
    @NonFinal PersonRepository personRepository;

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
    void testGetAllPersons() throws Exception {
        when(personRepository.findAll()).thenReturn(new ArrayList<>(room.getPersons()));

        mvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));

        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testPersonNotFound() {
        Throwable throwable = assertThrows(ServletException.class, () -> mvc.perform(get("/persons/30")).andExpect(status().isNotFound()));
        assertEquals(PersonNotFoundException.class, throwable.getCause().getClass());
    }

    @Test
    void testPostPerson() throws Exception {

        Person person = new Person("Peter Michelsen");
        person.setCountry(countriesMap.get("US"));
        person.setIsRegistered(false);
        when(personRepository.save(person)).thenReturn(person);

        mvc.perform(post("/persons")
                .content(new ObjectMapper().writeValueAsString(person))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Peter Michelsen")))
                .andExpect(jsonPath("$.country.codeName", is("US")))
                .andExpect(jsonPath("$.country.name", is("USA")))
                .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

        verify(personRepository, times(1)).save(person);

    }

    @Test
    void testPatchPerson() throws Exception {
        Person person = new Person("Sophia Graham");
        person.setCountry(countriesMap.get("UK"));
        person.setIsRegistered(false);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        String updates =
                "{\"name\":\"Sophia Jones\", \"country\":\"AU\", \"isRegistered\":\"true\"}";

        mvc.perform(patch("/persons/1")
                .content(updates)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testDeletePerson() throws Exception {

        mvc.perform(delete("/persons/4"))
                .andExpect(status().isOk());

        verify(personRepository, times(1)).deleteById(4L);
    }
}
