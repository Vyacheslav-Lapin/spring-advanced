package com.luxoft.data.examples;

import com.luxoft.data.examples.model.Address;
import com.luxoft.data.examples.model.Person;
import com.luxoft.data.examples.repositories.AddressRepository;
import com.luxoft.data.examples.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger-UI Url - http://localhost:8080/swagger-ui/
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
public class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

  @Bean
  public Docket personApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  @Bean
  public CommandLineRunner init(PersonRepository personRepository, AddressRepository addressRepository) {
    return env -> {
      generateTestData(personRepository, addressRepository);
      log.info("TEST DATA GENERATED");
    };
  }

  private void generateTestData(PersonRepository repository, AddressRepository addressRepository) {
    Address address11 = addressRepository.save(new Address("Darwin Street", "Kiev"));
    Address address12 = addressRepository.save(new Address("Defence Street", "Kiev"));
    Address address13 = addressRepository.save(new Address("Distant street", "Kiev"));

    Address address21 = addressRepository.save(new Address("Gas Street", "Kiev"));
    Address address22 = addressRepository.save(new Address("Gastello Street", "Kiev"));
    Address address23 = addressRepository.save(new Address("Gogol street", "Kiev"));

    repository.save(new Person("Ivan", 21, address11));
    repository.save(new Person("Alex", 27, address12));
    repository.save(new Person("Tomas", 21, address13));
    repository.save(new Person("Irina", 18, address21));
    repository.save(new Person("Anna", 21, address22));

    repository.save(new Person("Toma", 35, address23));
    repository.save(new Person("Ben", 38, address23));
    repository.save(new Person("Mike", 16, address23));
    repository.save(new Person("Ibrahim", 39, address12));
  }
}
