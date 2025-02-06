package com.luxoft.springadvanced.springrest.dao;

import com.luxoft.springadvanced.springrest.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
