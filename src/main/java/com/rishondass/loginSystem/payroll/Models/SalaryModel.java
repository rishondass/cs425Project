package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="salary",schema="payroll")
public class EmployeeModel {
    @Id
    @Column(name="salary")
    private int salary;

    @Column(name="salaryType")
    private String state;

    @Column(name="year")
    private int year;

    @Column(name="bonus")
    private int bonus;

    @Column(name="employeeID")
    private int employeeID;

    //@OneToMany(cascade=CascadeType.ALL,targetEntity = AddressModel.class, mappedBy = "employeeID",orphanRemoval = true,fetch = FetchType.LAZY)
    //private Set<AddressModel> addresses;

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getyear() {
        return year;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public String getBonus() {
        return bonus;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }    
}
