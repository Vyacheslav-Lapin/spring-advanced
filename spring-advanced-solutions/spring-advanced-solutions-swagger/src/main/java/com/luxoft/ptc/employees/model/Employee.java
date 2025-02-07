package com.luxoft.ptc.employees.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private Date dateOfBirth;

    @Embedded
    private List<String> phones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    @Column(name = "manager_id")
    private Long managerId;

    @ManyToOne
    @JoinColumn(name = "manager_id", insertable = false, updatable = false)
    private Employee manager;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Schema(name = "employeeSubordinates", description="TTTTT")
    List<Employee> subordinates;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Task> createdTasks;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Task> tasks;

    public void setId(Long id) {
        this.id = id;
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Operation(description="Get Subordinates", tags = {"Employee" })
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        //if (managerId != null) return managerId;
        //if (manager != null) return manager.getId();
        return managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
