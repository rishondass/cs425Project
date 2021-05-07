package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="statetax",schema="payroll")
public class StateTaxModel {
    @Id
    @Column(name="taxrate")
    private double taxRate;

    @Column(name="state")
    private String state;

    @Column(name="year")
    private int year;

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
