package com.luxoft.springadvanced.jpa.inheritance.dao;

import com.luxoft.springadvanced.jpa.inheritance.bean.Address;
import com.luxoft.springadvanced.jpa.inheritance.bean.Professional;
import com.luxoft.springadvanced.jpa.inheritance.bean.Student;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(PersonInheritanceImpl.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PersonInheritanceDaoTest {

  PersonInheritanceDao personDao;

  @Test
  @Disabled
  @DirtiesContext // reset context so in memory DB re-inits
  public void testSaveInDatabase() {
    val bill = new Student("MIT");
    bill.setId(4)
        .setFirstName("Bill")
        .setLastName("Fox");
    personDao.save(bill);

    val persons = personDao.findPersons();

    assertEquals(4, persons.size(),
                 () -> "Number of persons should be %d.".formatted(4));
  }

  @Test
  @Disabled
  @DirtiesContext // reset context so in memory DB re-inits
  public void testDeleteFromDatabase() {
    val michael = new Professional("Spring Coffee");
    michael.setId(3)
           .setFirstName("Michael")
           .setLastName("Johnson");

    personDao.delete(michael);

    assertEquals(2, personDao.findPersons().size(),
                 () -> "Number of persons should be %d.".formatted(2));
  }

  @Test
  @Disabled
  @DirtiesContext // reset context so in memory DB re-inits
  public void testQueryDatabase() {

    assertNotNull(personDao, "Person DAO is null.");

    val persons = personDao.findPersons();

    assertNotNull(persons, "Person list is null.");
    assertEquals(3, persons.size(), "Number of persons should be " + 3 + ".");

    val firstId = 1;
    val secondId = 2;

    for (val person : persons) {
      assertNotNull(person, "Person is null.");

      if (firstId == person.getId()) {
        val firstName = "Joe";
        val lastName = "Smith";
        val schoolName = "NYU";

        int expectedAddresses = 1;

        assertEquals(firstName, person.getFirstName(), () -> "Person first name should be %s.".formatted(firstName));
        assertEquals(lastName, person.getLastName(), () -> "Person last name should be %s.".formatted(lastName));

        assertNotNull(person.getAddresses(), "Person's address list is null.");
        assertEquals(expectedAddresses, person.getAddresses().size(), () -> "Number of person's address list should be %d.".formatted(expectedAddresses));

        assertInstanceOf(Student.class, person, "Person should be an instance of student.");
        assertEquals(schoolName, ((Student) person).getSchoolName(), () -> "School name should be %s.".formatted(schoolName));

        val addressId = 1;
        val addr = "1060 West Addison St.";
        val city = "Chicago";
        val state = "IL";

        for (Address address : person.getAddresses()) {
          assertNotNull(address, "Address is null.");

          assertEquals(addressId, address.getId(), () -> "Address id should be '%d'.".formatted(addressId));
          assertEquals(addr, address.getAddress(), () -> "Address address should be '%s'.".formatted(address));

          assertEquals(city, address.getCity(), () -> "Address city should be '%s'.".formatted(city));
          assertEquals(state, address.getState(), () -> "Address state should be '%s'.".formatted(state));
        }
      } else if (secondId == person.getId()) {
        val firstName = "John";
        val lastName = "Wilson";
        val companyName = "Spring Pizza";

        val expectedAddresses = 2;

        assertEquals(firstName, person.getFirstName(), () -> "Person first name should be %s.".formatted(firstName));
        assertEquals(lastName, person.getLastName(), () -> "Person last name should be %s.".formatted(lastName));

        assertNotNull(person.getAddresses(), "Person's address list is null.");
        assertEquals(expectedAddresses, person.getAddresses().size(), "Number of person's address list should be %d.".formatted(expectedAddresses));

        assertInstanceOf(Professional.class, person, "Person should be an instance of professional.");
        assertEquals(companyName, ((Professional) person).getCompanyName(), "Company name should be %s.".formatted(companyName));

        val addressId = 3;
        val addr = "47 Howard St.";
        val city = "San Francisco";
        val state = "CA";

        for (Address address : person.getAddresses()) {
          assertNotNull(address, "Address is null.");

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
