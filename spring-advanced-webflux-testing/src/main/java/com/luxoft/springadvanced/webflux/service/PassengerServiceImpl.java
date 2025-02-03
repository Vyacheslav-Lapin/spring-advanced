package com.luxoft.springadvanced.webflux.service;

import com.luxoft.springadvanced.webflux.dao.PassengerRepository;
import com.luxoft.springadvanced.webflux.model.Passenger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
 
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
     
  @Delegate
  PassengerRepository passengerRepository;
 
    public Mono<Passenger> create(Passenger e) {
        val passenger = passengerRepository.save(e);
        passenger.subscribe();
        return passenger;
    }

  @Override
  public Mono<Passenger> save(Passenger e) {
    return passengerRepository.save(e);
  }
}
