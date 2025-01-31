package ru.ibs.training.java.spring.task01.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ru.ibs.training.java.spring.task01.bean.Address;
import ru.ibs.training.java.spring.task01.bean.Person;
import ru.ibs.training.java.spring.task01.bean.Professional;
import ru.ibs.training.java.spring.task01.bean.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class PersonInheritanceDaoTest {

    @Autowired
    protected PersonInheritanceDao personDao;

    @Test
    @DirtiesContext // reset context so in memory DB re-inits
    public void testSaveInDatabase() {
        Student bill = new Student();
        bill.setId(4);
        bill.setFirstName("Bill");
        bill.setLastName("Fox");
        bill.setSchoolName("MIT");
        personDao.save(bill);

        Collection<Person> persons = personDao.findPersons();

        int expected = 4;
        assertEquals(expected, persons.size(), () -> "Number of persons should be " + expected + ".");
    }

    @Test
    @DirtiesContext // reset context so in memory DB re-inits
    public void testQueryDatabase() {
        assertNotNull(personDao, () -> "Person DAO is null.");
        
        Collection<Person> persons = personDao.findPersons();

        int expected = 3;
        
        assertNotNull(persons, () -> "Person list is null.");
        assertEquals(expected, persons.size(), () -> "Number of persons should be " + expected + ".");
        
        int firstId = 1;
        int secondId = 2;
        
        for (Person person : persons) {
            assertNotNull(person, () -> "Person is null.");
            
            if (firstId == person.getId()) {
                String firstName = "Joe";
                String lastName = "Smith";
                String schoolName = "NYU";
                
                int expectedAddresses = 1;

            	assertEquals(firstName, person.getFirstName(), () -> "Person first name should be " + firstName + ".");
                assertEquals(lastName, person.getLastName(), () -> "Person last name should be " + lastName + ".");
            
                assertNotNull(person.getAddresses(), () -> "Person's address list is null.");
                assertEquals(expectedAddresses, person.getAddresses().size(), () -> "Number of person's address list should be " + expectedAddresses + ".");
                
                assertTrue((person instanceof Student), () -> "Person should be an instance of student.");
                assertEquals(schoolName, ((Student)person).getSchoolName(), () -> "School name should be " + schoolName + ".");
                
                int addressId = 1;
                String addr = "1060 West Addison St.";
                String city = "Chicago";
                String state = "IL";

                for (Address address : person.getAddresses()) {
                	assertNotNull(address, () -> "Address is null.");
                	
                	assertEquals(addressId, address.getId(), () -> "Address id should be '" + addressId + "'.");
                	assertEquals(addr, address.getAddress(), () -> "Address address should be '" + address + "'.");
                	
                	assertEquals(city, address.getCity(), () -> "Address city should be '" + city + "'.");
                	assertEquals(state, address.getState(), () -> "Address state should be '" + state + "'.");
                }
            } else if (secondId == person.getId()) {
                String firstName = "John";
                String lastName = "Wilson";
                String companyName = "Spring Pizza";
                
                int expectedAddresses = 2;

            	assertEquals(firstName, person.getFirstName(), () -> "Person first name should be " + firstName + ".");
                assertEquals(lastName, person.getLastName(), () -> "Person last name should be " + lastName + ".");
            
                assertNotNull(person.getAddresses(), () -> "Person's address list is null.");
                assertEquals(expectedAddresses, person.getAddresses().size(), () -> "Number of person's address list should be " + expectedAddresses + ".");

                assertTrue((person instanceof Professional), () -> "Person should be an instance of professional.");
                assertEquals(companyName, ((Professional)person).getCompanyName(), () -> "Company name should be " + companyName + ".");
                
                int addressId = 3;
                String addr = "47 Howard St.";
                String city = "San Francisco";
                String state = "CA";

                for (Address address : person.getAddresses()) {
                	assertNotNull(address, () -> "Address is null.");
                	
                	if (addressId == address.getId()) {
	                	assertEquals(addressId, address.getId(), () -> "Address id should be '" + addressId + "'.");
	                	assertEquals(addr, address.getAddress(), () -> "Address address should be '" + address + "'.");
	                	
	                	assertEquals(city, address.getCity(), () -> "Address city should be '" + city + "'.");
	                	assertEquals(state, address.getState(), () -> "Address state should be '" + state + "'.");
                	}
                }
            }

        }
    }
    
}
