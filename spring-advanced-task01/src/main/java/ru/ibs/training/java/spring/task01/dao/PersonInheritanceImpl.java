package ru.ibs.training.java.spring.task01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Setter;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.training.java.spring.task01.bean.Person;

import java.util.Collection;

import static org.springframework.transaction.annotation.Propagation.*;

@Repository
public class PersonInheritanceImpl implements PersonInheritanceDao {

  @Setter(onMethod_ = @PersistenceContext)
  @NonFinal EntityManager em;

  @SuppressWarnings("unchecked")
  public Collection<Person> findPersons() {
    return em.createQuery("select p from Person p order by p.lastName, p.firstName")
             .getResultList();
  }

  @Transactional(propagation = REQUIRES_NEW)
  public Person save(Person person) {
    return em.merge(person);
  }
}
