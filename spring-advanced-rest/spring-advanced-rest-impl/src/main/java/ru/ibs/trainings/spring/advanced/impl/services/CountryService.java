package ru.ibs.trainings.spring.advanced.impl.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.dto.CountryDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

  @Delegate
  CountryRepository repository;

  public List<CountryDto> findAll() {
    return repository.findAll().stream()
                     .map(country -> CountryDto.builder()
                                               .codeName(country.getCodeName())
                                               .name(country.getName()).build())
                     .toList();
  }
}
