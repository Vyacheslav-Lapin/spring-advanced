package com.luxoft.springadvanced.webflux.service;

import com.luxoft.springadvanced.webflux.dao.PassengerRepository;
import com.luxoft.springadvanced.webflux.model.Passenger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassengerService {
  Mono<Passenger> create(Passenger e);

  Mono<Passenger> findById(Integer id);

  Flux<Passenger> findByName(String name);

  Flux<Passenger> findAll();

  Mono<Passenger> update(Passenger e);

  Mono<Void> delete(Integer id);
}

@Service
@RequiredArgsConstructor
class PassengerServiceImpl implements PassengerService {

  @Delegate
  PassengerRepository passengerRepository;

  public Mono<Passenger> create(Passenger e) {
    Mono<Passenger> passenger = passengerRepository.save(e);
    passenger.subscribe();
    return passenger;
  }

  public Mono<Passenger> update(Passenger e) {
    return passengerRepository.save(e);
  }

  public Mono<Void> delete(Integer id) {
    return passengerRepository.deleteById(id);
  }

}
