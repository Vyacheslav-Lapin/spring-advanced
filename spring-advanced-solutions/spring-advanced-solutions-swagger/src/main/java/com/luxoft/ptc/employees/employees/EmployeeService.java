package com.luxoft.ptc.employees.employees;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

public interface EmployeeService extends EmployeeRepository {
  Iterable<Employee> findByExample(Employee probe);
}

@Service
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {

  @Delegate
  EmployeeRepository employeeRepository;

  @Override
  public Iterable<Employee> findByExample(Employee probe) {
    Optional.ofNullable(probe.getManagerId())
            .flatMap(employeeRepository::findById)
            .map(probe::setManager)
            .orElseThrow(() -> new RestClientException("Manager not found"));
    return employeeRepository.findAll(Example.of(probe));
  }

  //todo 10.02.2025: Написать багу в https://youtrack.jetbrains.com/issues : если закомментировать этот метод - всё собирается, т.е. он делегируется при сборке без проблем, проблема только в IDEA'вском plugin'е - он тупит и подчёркивает красным класс, по-видимому где-то счётчик не доходит до нужной позиции и заканчивается не дойдя до конца списка делегируемых методов...
  @Override
  public <S extends Employee> S saveAndFlush(S entity) {
    return employeeRepository.saveAndFlush(entity);
  }
}
