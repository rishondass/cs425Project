package com.rishondass.loginSystem.payroll.Models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name="address",schema="payroll")
public class AddressModel {
    @Id
    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zipcode")
    private int zipCode;

    @Column(name="country")
    private String country;

    @Column(name="employeeid")
    private int employeeID;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "employeeID", insertable = false, updatable = false),
/*            @JoinColumn(name = "ssn", insertable = false, updatable = false)*/
    })
    @Fetch(FetchMode.JOIN)
    private EmployeeModel employeeModel;

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String toString(){
        return getAddress()+", "+ getCity()+", "+getState()+", "+getZipCode()+", "+getCountry();
    }
}
