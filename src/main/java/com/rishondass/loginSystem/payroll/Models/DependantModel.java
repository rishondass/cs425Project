package com.rishondass.loginSystem.payroll.Models;

import com.rishondass.loginSystem.payroll.IDS.DependantIDS;

import javax.persistence.*;

@Entity
@Table(name="dependant",schema="payroll")
@IdClass(DependantIDS.class)
public class DependantModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Id
    @Column(name="ssn")
    private String ssn;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "relation")
    private String relation;

    @Column(name = "parentssn")
    private String parentSSN;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getParentSSN() {
        return parentSSN;
    }

    public void setParentSSN(String parentSSN) {
        this.parentSSN = parentSSN;
    }
}
