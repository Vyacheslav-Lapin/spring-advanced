package ru.ibs.training.java.spring.data.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.training.java.spring.data.rest.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
