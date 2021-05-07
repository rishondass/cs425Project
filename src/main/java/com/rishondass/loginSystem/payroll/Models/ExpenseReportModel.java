package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="expensereports",schema = "payroll")
public class ExpenseReportModel {
    @Id
    @Column(name="daterange")
    private String dateRange;

    @Column(name="totalexpense",insertable = false,updatable = false)
    private double totalExpense;

    @Column(name="ssn")
    private String ssn;

    @Column(name="grossincome")
    private double grossIncome;


    @Column(name="totaldeductions")
    private double totalDeductions;

    @Column(name="extraearnings")
    private double extraEarnings;

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getExtraEarnings() {
        return extraEarnings;
    }

    public void setExtraEarnings(double extraEarnings) {
        this.extraEarnings = extraEarnings;
    }
}
