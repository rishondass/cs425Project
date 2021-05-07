package com.rishondass.loginSystem.payroll.Models;

import com.rishondass.loginSystem.payroll.IDS.BenefitsIDs;

import javax.persistence.*;


@Entity
@Table(name="benefits",schema="payroll")
@IdClass(BenefitsIDs.class)
public class BenefitsModel {

    @Id
    @Column(name="benefittype")
    private String benefitType;

    @Id
    @Column(name="benefitname")
    private String benefitName;

    @Column(name="rate")
    private double rate;

    @Column(name="companycontribution")
    private double companyContribution;

    @Column(name= "numfamilymembers")
    private int numFamilyMembers;

    @Column(name="costperfamily",insertable = false,updatable = false)
    private double costPerFamily;

    @Column(name="costperperson",insertable = false,updatable = false)
    private double costPerPerson;




    @Column(name="childssn")
    private String childSsn;

    @Column(name="parentssn")
    private String parentSsn;

    public int getNumFamilyMembers() {
        return numFamilyMembers;
    }

    public void setNumFamilyMembers(int numFamilyMembers) {
        this.numFamilyMembers = numFamilyMembers;
    }

    public String getBenefitType() {
        return benefitType;
    }

    public void setBenefitType(String benefitType) {
        this.benefitType = benefitType;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setBenefitName(String benefitName) {
        this.benefitName = benefitName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCompanyContribution() {
        return companyContribution;
    }

    public void setCompanyContribution(double companyContribution) {
        this.companyContribution = companyContribution;
    }

    public double getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(double costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public double getCostPerFamily() {
        return costPerFamily;
    }

    public void setCostPerFamily(double costPerFamily) {
        this.costPerFamily = costPerFamily;
    }

    public String getChildSsn() {
        return childSsn;
    }

    public void setChildSsn(String childSsn) {
        this.childSsn = childSsn;
    }

    public String getParentSsn() {
        return parentSsn;
    }

    public void setParentSsn(String parentSsn) {
        this.parentSsn = parentSsn;
    }

}
