package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;

@Entity
@Table(name="dependent",schema="payroll")
public class DependentModel {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "firstName")
    private String firstName;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lastName")
    private String lastName;

    @Column(name="ssn")
    private String ssn;

    @Column(name = "relation")
    private String relation;

    @Column(name = "parentSSN")
    private String parentSSN;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSSN() {
        return ssn;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getParentSSN() {
        return parentSSN;
    }

    public void setParentSSN(String parentSSN) {
        this.parentSSN = parentSSN;
    }
}
