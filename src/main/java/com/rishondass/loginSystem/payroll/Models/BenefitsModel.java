package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="benefits",schema="payroll")
public class EmployeeModel {
    @Id
    @Column(name="benefitType")
    private String benefitType;

    @Column(name="benefitName")
    private String benefitName;

    @Column(name="rate")
    private int rate;

    @Column(name="companyContribution")
    private int companyContribution;

    @Column(name="costPerPerson")
    private int costPerPerson;

    @Column(name="costPerFamily")
    private int costPerFamily;

    @Column(name="ssn")
    @Pattern(message="Invalid SSN Format", regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$")
    public String ssn;

    @Column(name = "parentSSN")
    private String parentSSN;

    //@OneToMany(cascade=CascadeType.ALL,targetEntity = AddressModel.class, mappedBy = "employeeID",orphanRemoval = true,fetch = FetchType.LAZY)
    //private Set<AddressModel> addresses;

    public void setBenefitType(String benefitType) {
        this.benefitType = benefitType;
    }

    public String getBenefitType() {
        return benefitType;
    }

    public void setBenefitName(String benefitName) {
        this.state = benefitName;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setRate(int rate) {
        this.year = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setCompanyContribution(int companyContribution) {
        this.companyContribution = companyContribution;
    }

    public int getCompanyContribution() {
        return companyContribution;
    }

    public void setCostPerPerson(int costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public int getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerFamily(int costPerFamily) {
        this.costPerFamily = costPerFamily;
    }

    public int getCostPerFamily() {
        return costPerFamily;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public int getParentSSN() {
        return parentSSN;
    }

    public void setParentSSN(String parentSSN) {
        this.parentSSN = parentSSN;
    }
