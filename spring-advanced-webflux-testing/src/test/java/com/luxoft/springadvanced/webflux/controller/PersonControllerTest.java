package com.luxoft.springadvanced.webflux.controller;

import com.luxoft.springadvanced.webflux.dao.PersonRepository;
import com.luxoft.springadvanced.webflux.model.Country;
import com.luxoft.springadvanced.webflux.model.Person;
import com.luxoft.springadvanced.webflux.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Import(PersonService.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PersonController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PersonControllerTest {

  WebTestClient webClient;

  @MockitoBean
  @NonFinal PersonRepository repository;

  @Test
  void getPersons() {
    val person = new Person(1L, "Вася Пупкин", LocalDate.now(),
                            new Country("NK", "North Korea"),
                            false, true);

    Mockito.when(repository.findAll())
           .thenReturn(Flux.just(person));

    webClient.get().uri("/custom-persons")
             .accept(MediaType.APPLICATION_JSON)
             .exchange()
             .expectStatus()
//             .isOk()
             .is4xxClientError()
             .expectBodyList(Person.class);

//    Mockito.verify(repository, times(1)).findAll();
  }
}
