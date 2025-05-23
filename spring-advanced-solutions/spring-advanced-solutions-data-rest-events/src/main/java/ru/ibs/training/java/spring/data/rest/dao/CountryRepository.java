package ru.ibs.training.java.spring.data.rest.dao;

import ru.ibs.training.java.spring.data.rest.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
