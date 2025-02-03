package ru.ibs.training.java.spring.task01.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.ibs.training.java.spring.task01.bean.Address;
import ru.ibs.training.java.spring.task01.bean.Professional;
import ru.ibs.training.java.spring.task01.bean.Student;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PersonInheritanceDaoTest {

  PersonInheritanceDao personDao;

  @Test
  @DirtiesContext
    // reset context so in memory DB re-inits
  void testSaveInDatabase() {
    // given
    val bill = new Student("MIT");
    bill.setFirstName("Bill")
        .setLastName("Fox");

    personDao.save(bill);

    // when
    assertThat(personDao.findPersons()).isNotNull()
        // then
        .hasSize(1);
  }

  @Test
  @DirtiesContext // reset context so in memory DB re-inits
  void testQueryDatabase() {
    assertNotNull(personDao, "Person DAO is null.");

    val persons = personDao.findPersons();

    int expectedSize = 0;

    assertNotNull(persons, "Person list is null.");
    assertEquals(expectedSize, persons.size(), () -> "Number of persons should be %d.".formatted(expectedSize));

    int firstId = 1;
    int secondId = 2;

    for (val person : persons) {
      assertNotNull(person, () -> "Person is null.");

      if (firstId == person.getId()) {
        String firstName = "Joe";
        String lastName = "Smith";
        String schoolName = "NYU";

        int expectedAddresses = 1;

        assertEquals(firstName, person.getFirstName(), () -> "Person first name should be %s.".formatted(firstName));
        assertEquals(lastName, person.getLastName(), () -> "Person last name should be %s.".formatted(lastName));

        assertNotNull(person.getAddresses(), "Person's address list is null.");
        assertEquals(expectedAddresses, person.getAddresses().size(), () -> "Number of person's address list should be %d.".formatted(expectedAddresses));

        assertTrue((person instanceof Student), "Person should be an instance of student.");
        assertEquals(schoolName, ((Student) person).getSchoolName(), () -> "School name should be %s.".formatted(schoolName));

        int addressId = 1;
        String addr = "1060 West Addison St.";
        String city = "Chicago";
        String state = "IL";

        for (Address address : person.getAddresses()) {
          assertNotNull(address, "Address is null.");

          assertEquals(addressId, address.getId(), () -> "Address id should be '%d'.".formatted(addressId));
          assertEquals(addr, address.getAddress(), () -> "Address address should be '%s'.".formatted(address));

          assertEquals(city, address.getCity(), () -> "Address city should be '%s'.".formatted(city));
          assertEquals(state, address.getState(), () -> "Address state should be '%s'.".formatted(state));
        }
      } else if (secondId == person.getId()) {
        String firstName = "John";
        String lastName = "Wilson";
        String companyName = "Spring Pizza";

        int expectedAddresses = 2;

        assertEquals(firstName, person.getFirstName(), () -> "Person first name should be %s.".formatted(firstName));
        assertEquals(lastName, person.getLastName(), () -> "Person last name should be %s.".formatted(lastName));

        assertNotNull(person.getAddresses(), () -> "Person's address list is null.");
        assertEquals(expectedAddresses, person.getAddresses().size(), () -> "Number of person's address list should be %d.".formatted(expectedAddresses));

        assertInstanceOf(Professional.class, person, "Person should be an instance of professional.");
        assertEquals(companyName, ((Professional) person).getCompanyName(), () -> "Company name should be %s.".formatted(companyName));

        int addressId = 3;
        String addr = "47 Howard St.";
        String city = "San Francisco";
        String state = "CA";

        for (Address address : person.getAddresses()) {
          assertNotNull(address, () -> "Address is null.");

          if (addressId == address.getId()) {
            assertEquals(addressId, address.getId(), () -> "Address id should be '%d'.".formatted(addressId));
            assertEquals(addr, address.getAddress(), () -> "Address address should be '%s'.".formatted(address));

            assertEquals(city, address.getCity(), () -> "Address city should be '%s'.".formatted(city));
            assertEquals(state, address.getState(), () -> "Address state should be '%s'.".formatted(state));
          }
        }
      }

    }
  }
}
