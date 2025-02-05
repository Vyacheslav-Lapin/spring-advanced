package com.luxoft.springadvanced.webflux.service;

import com.luxoft.springadvanced.webflux.dao.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

  @Delegate
  PersonRepository personRepository;
}
