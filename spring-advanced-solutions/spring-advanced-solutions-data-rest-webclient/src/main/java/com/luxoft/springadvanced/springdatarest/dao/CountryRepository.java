package com.luxoft.springadvanced.springdatarest.dao;

import com.luxoft.springadvanced.springdatarest.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
