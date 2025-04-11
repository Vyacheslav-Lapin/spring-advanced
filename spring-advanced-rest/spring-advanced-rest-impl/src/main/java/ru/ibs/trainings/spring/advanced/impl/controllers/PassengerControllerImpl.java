package ru.ibs.trainings.spring.advanced.impl.controllers;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import ru.ibs.trainings.spring.advanced.impl.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.PassengerMapper;
import ru.ibs.trainings.spring.api.PassengerController;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.ibs.trainings.spring.dto.PassengerDto.Fields.*;

@RestController
@RequiredArgsConstructor
@ExtensionMethod(value = PassengerMapper.class, suppressBaseMethods = false)
public class PassengerControllerImpl implements PassengerController {

  PassengerRepository repository;
  Map<String, CountryDto> countriesMap;

  @Override
  public ResponseEntity<List<PassengerDto>> findAll() {
    val all = repository.findAll().stream()
                        .map(passenger -> PassengerDto.builder()
                                                      .id(passenger.getId())
                                                      .name(passenger.getName())
                                                      .isRegistered(passenger.isRegistered())
                                                      .country(CountryDto.builder()
                                                                         .name(passenger.getCountry().getName())
                                                                         .codeName(passenger.getCountry().getCodeName()).build()).build())
                        .toList();
    return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
  }

  @Override
  public ResponseEntity<PassengerDto> createPassenger(PassengerDto passenger, @NotNull Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    val passengerDto = repository.save(passenger.toPassengerEntity())
                                 .toPassengerDto();
    return ResponseEntity.created(URI.create("/passengers/" + passengerDto.id()))
                         .body(passengerDto);
  }

  @Override
  public ResponseEntity<PassengerDto> findPassenger(Long id) {
    return repository.findById(id)
                     .map(PassengerMapper::toPassengerDto)
                     .map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<PassengerDto> patchPassenger(Map<String, String> updates, Long id) {

    return repository.findById(id)
                     .map(passenger -> {

                       Optional.ofNullable(updates.get(NAME)).ifPresent(passenger::setName);

                       Optional.ofNullable(updates.get(COUNTRY))
                               .map(countriesMap::get)
                               .map(CountryMapper::toCountryEntity)
                               .ifPresent(passenger::setCountry);

                       Optional.ofNullable(updates.get(IS_REGISTERED))
                               .filter("true"::equalsIgnoreCase)
                               .ifPresent(__ -> passenger.setRegistered(true));

                       return repository.save(passenger).toPassengerDto();
                     })
                     .map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
