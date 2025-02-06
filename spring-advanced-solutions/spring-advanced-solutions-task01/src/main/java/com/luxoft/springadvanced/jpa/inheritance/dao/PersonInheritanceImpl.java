package com.luxoft.springadvanced.jpa.inheritance.dao;

import com.luxoft.springadvanced.jpa.inheritance.bean.Person;
import lombok.Setter;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

  @Transactional(propagation = REQUIRES_NEW)
  public void delete(Person person) {
    em.remove(em.merge(person));
  }
}
