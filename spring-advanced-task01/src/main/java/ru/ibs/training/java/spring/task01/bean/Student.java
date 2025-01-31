package ru.ibs.training.java.spring.task01.bean;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="PERSON_STUDENT")
@DiscriminatorValue("1")
public class Student extends Person {

    private static final long serialVersionUID = -8933409594928827120L;

    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    
}
