package ru.ibs.trainings.spring.advanced.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.ibs.trainings.spring.advanced.impl.common.TestUtils;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.impl.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.impl.mappers.CollectionMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.PassengerMapper;
import ru.ibs.trainings.spring.advanced.impl.model.Passenger;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.FlightDto;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest//(classes = {FlightConfig.class, Application.class})
@AutoConfigureMockMvc
//@Import(FlightConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtensionMethod(suppressBaseMethods = false,
                 value = {
                     CollectionMapper.class,
                     CountryMapper.class,
                     TestUtils.class,
                 })
@SuppressWarnings("java:S125")
class RestApplicationTest {

  MockMvc mvc;
  FlightDto flight;
  Map<String, CountryDto> countriesMap;

  @Value("${springdoc.api-docs.path:/v3/api-docs}")
  String apiDocsPath;

  ObjectMapper objectMapper;

  @SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
  @MockitoBean @NonFinal PassengerRepository passengerRepository;

  @SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
  @MockitoBean @NonFinal CountryRepository countryRepository;

  @Test
  @SuppressWarnings({"java:S2699", "java:S1135"})
  void contextLoads() {
    // this method is empty because it tests a Spring application context load
  }

  @Test
  void testGetAllCountries() throws Exception {
    when(countryRepository.findAll())
        .thenReturn(countriesMap.values()
                                .transformList(CountryMapper::toCountryEntity));

    mvc.perform(get("/custom-countries"))
       .andExpect(status().isOk())
       .andExpect(content().contentType(APPLICATION_JSON))
       .andExpect(jsonPath("$", hasSize(3)));

    verify(countryRepository, times(1)).findAll();
  }

  @Test
  void testGetAllPassengers() throws Exception {
    when(passengerRepository.findAll())
        .thenReturn(flight.passengers()
                          .transformList(PassengerMapper::toPassengerEntity));

    mvc.perform(get("/passengers"))
       .andExpect(status().isOk())
       .andExpect(content().contentType(APPLICATION_JSON))
       .andExpect(jsonPath("$", hasSize(20)));

    verify(passengerRepository, times(1)).findAll();
  }

  @SneakyThrows
  @Test
  void testPassengerNotFound() {
    mvc.perform(get("/passengers/30"))
       .andExpect(status().isNotFound());
  }

  @Test
//  @Disabled
  void testPostPassenger() throws Exception {

    val passenger = new Passenger("Peter Michelsen")
        .setCountry(countriesMap.get("US").toCountryEntity())
        .setId(1L);

    when(passengerRepository.save(passenger)).thenReturn(passenger);

    val resultActions = mvc.perform(post("/passengers")
                                        .content(objectMapper.writeValueAsString(passenger))
                                        .header(CONTENT_TYPE, APPLICATION_JSON))
                           .andExpect(status().isCreated());

//    val response = resultActions.andReturn()
//                                .getResponse()
//                                .getContentAsString();

    resultActions
//        .andExpect(jsonPath("$.name", is("Peter Michelsen")))
//        .andExpect(jsonPath("$.country.codeName", is("US")))
//        .andExpect(jsonPath("$.country.name", is("USA")))
        .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

    verify(passengerRepository, times(1)).save(passenger);

  }

  @Test
  void testPatchPassenger() throws Exception {
    // given:
    val passenger = new Passenger("Sophia Graham")
        .setCountry(countriesMap.get("UK").toCountryEntity());

    val passengerUpdates = "{\"name\":\"Sophia Jones\", \"country\":\"AU\", \"isRegistered\":\"true\"}";

    when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
    when(passengerRepository.save(passenger)).thenReturn(passenger);

    // when:
    mvc.perform(patch("/passengers/1")
                    .content(passengerUpdates)
                    .header(CONTENT_TYPE, APPLICATION_JSON))
       .andExpect(content().contentType(APPLICATION_JSON))
       .andExpect(status().isOk());

    // then:
    verify(passengerRepository, times(1)).findById(1L);
    verify(passengerRepository, times(1)).save(passenger);
  }

  @Test
  @SneakyThrows
  @DisplayName("Openapi documentation generated correctly")
  void openapiDocumentationGeneratedCorrectlyTest() {
    val contentAsString = mvc.perform(MockMvcRequestBuilders.get(apiDocsPath + ".yaml"))
                             .andExpect(MockMvcResultMatchers.status().isOk())
                             .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.oai.openapi"))
                             .andReturn().getResponse().getContentAsString();

    contentAsString.saveToGeneratedSourceFile("openapi.yaml");
  }

//    PassengerControllerClient client;
//
//    @Test
//    @DisplayName(" works correctly")
//    void worksCorrectlyTest() {
//         given
//        val passenger = client.findPassenger(14325L);
//         when
//        assertThat(passenger).isNotNull()
//                              then
//                             .extracting(Passenger::getCountry)
//                             .extracting(Country::getCodeName)
//                             .isEqualTo("NK");
//    }
}
