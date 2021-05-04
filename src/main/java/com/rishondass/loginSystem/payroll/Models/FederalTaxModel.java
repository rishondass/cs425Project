package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="stateTaxRate",schema="payroll")
public class EmployeeModel {
    @Id
    @Column(name="taxRate")
    private int taxRate;

    @Column(name="country")
    private String country;

    @Column(name="year")
    private int year;

    //@OneToMany(cascade=CascadeType.ALL,targetEntity = AddressModel.class, mappedBy = "employeeID",orphanRemoval = true,fetch = FetchType.LAZY)
    //private Set<AddressModel> addresses;

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getcountry() {
        return country;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getyear() {
        return year;
    }
}
