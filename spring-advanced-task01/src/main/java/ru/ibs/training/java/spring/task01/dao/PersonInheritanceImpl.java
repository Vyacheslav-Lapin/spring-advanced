package ru.ibs.training.java.spring.task01.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ibs.training.java.spring.task01.bean.Person;

import java.util.Collection;

@Repository
public class PersonInheritanceImpl implements PersonInheritanceDao {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Collection<Person> findPersons() {
        return em.createQuery("select p from Person p order by p.lastName, p.firstName").getResultList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Person save(Person person) {
        return em.merge(person);
    }

}
