package com.rishondass.loginSystem.payroll.Models;

import com.rishondass.loginSystem.payroll.IDS.SalaryIDs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="salary",schema="payroll")
@IdClass(SalaryIDs.class)
public class SalaryModel implements Serializable {
    @Id
    @Column(name="salarybase")
    private double salaryBase;

    @Id
    @Column(name="year")
    private int year;

    @Column(name="salarytype")
    private String salaryType;

    @Column(name="bonus")
    private double bonus;

    @Column(name="employeeid")
    private int employeeID;

    @Column(name="salarytotal",insertable = false)
    private double salaryTotal;

    public double getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(double salaryBase) {
        this.salaryBase = salaryBase;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public double getSalaryTotal() {
        return salaryTotal;
    }

    public void setSalaryTotal(double salaryTotal) {
        this.salaryTotal = salaryTotal;
    }
}
