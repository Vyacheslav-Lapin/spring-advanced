package com.luxoft.springadvanced.webflux.controller;

import com.luxoft.springadvanced.webflux.dao.PassengerRepository;
import com.luxoft.springadvanced.webflux.model.Passenger;
import com.luxoft.springadvanced.webflux.service.PassengerServiceImpl;
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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(PassengerServiceImpl.class)
@WebFluxTest(controllers = PassengerController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PassengerControllerTest {

  WebTestClient webClient;

  @MockitoBean
  @NonFinal PassengerRepository repository;

  @Test
  void testCreatePassenger() {
    val passenger = new Passenger()
        .setId(1)
        .setName("John Smith")
        .setCoveredDistance(1_000);

    Mockito.when(repository.save(passenger))
           .thenReturn(Mono.just(passenger));

    webClient.post()
             .uri("/create")
             .contentType(MediaType.APPLICATION_JSON)
             .body(BodyInserters.fromValue(passenger))
             .exchange()
             .expectStatus().isCreated();

    Mockito.verify(repository, times(1)).save(passenger);
  }

  @Test
  void testGetPassengersByName() {
    val passenger = new Passenger()
        .setId(1)
        .setName("John Smith")
        .setCoveredDistance(1_000);

    val passengerFlux = Flux.fromIterable(List.of(passenger));

    Mockito
        .when(repository.findByName("John Smith"))
        .thenReturn(passengerFlux);

    webClient.get().uri("/name/{name}", "John Smith")
             .accept(MediaType.APPLICATION_JSON)
             .exchange()
             .expectStatus().isOk()
             .expectBodyList(Passenger.class);

    Mockito.verify(repository, times(1)).findByName("John Smith");
  }

  @Test
  void testGetPassengerById() {
    //todo: Create a passenger, set its id, name and distance covered


    //todo: Instruct the mock repository to return this passenger when finding its id


    //todo: Use webClient to get the URL of the passenger, check the status to be OK and the JSON paths of the response


    //todo: Verify that the findById method with the given id has been invoked by the repository exactly once
  }

}
