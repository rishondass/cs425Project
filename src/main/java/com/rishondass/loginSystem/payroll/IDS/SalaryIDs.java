package com.rishondass.loginSystem.payroll.IDS;

import java.io.Serializable;

public class SalaryIDs implements Serializable {
    private double salaryBase;
    private int year;

    public SalaryIDs() {
        this.salaryBase = 0;
        this.year = 0;
    }

    public SalaryIDs(double salary, int year) {
        this.salaryBase = salary;
        this.year = year;
    }
}
