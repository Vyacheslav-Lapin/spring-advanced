package com.luxoft.springadvanced.springrest.dao;

import com.luxoft.springadvanced.springrest.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
