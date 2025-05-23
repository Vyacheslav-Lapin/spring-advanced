package ru.ibs.trainings.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ibs.trainings.spring.dto.PassengerDto;

import java.util.List;
import java.util.Map;

public interface PassengerController {

  @GetMapping("/passengers")
  ResponseEntity<List<PassengerDto>> findAll();

  @PostMapping("/passengers")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<PassengerDto> createPassenger(@RequestBody PassengerDto passenger, Errors errors);

  @GetMapping("/passengers/{id}")
  ResponseEntity<PassengerDto> findPassenger(@PathVariable Long id);

  @PatchMapping("/passengers/{id}")
  ResponseEntity<PassengerDto> patchPassenger(@RequestBody Map<String, String> updates, @PathVariable Long id);
}
