package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paycheck",schema = "payroll")
public class PaycheckModel {
    @Id
    @Column(name="daterange")
    private String dateRange;

    @Column(name="concatdaterange")
    private String concatDateRange;

    @Column(name="checktotal",insertable = false,updatable = false)
    private double checkTotal;

    @Column(name="ssn")
    private String ssn;

    @Column(name="grossincome")
    private double grossIncome;

    @Column(name="taxcut")
    private double taxCut;

    @Column(name="totaldeductions")
    private double totalDeductions;

    public String getConcatDateRange() {
        return concatDateRange;
    }

    public void setConcatDateRange(String concatDateRange) {
        this.concatDateRange = concatDateRange;
    }

    public double getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(double checkTotal) {
        this.checkTotal = checkTotal;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
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

    public double getTaxCut() {
        return taxCut;
    }

    public void setTaxCut(double taxCut) {
        this.taxCut = taxCut;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }
}
