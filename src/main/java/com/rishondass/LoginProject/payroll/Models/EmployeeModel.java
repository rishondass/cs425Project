package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee",schema="payroll")
public class EmployeeModel {
    @Id
    @Column(name="employeeid")
    private int employeeID;

    @Id
    @Column(name="ssn")
    private String ssn;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(name="jobtitle")
    private String jobTitle;

    @Column(name="jobperformance")
    private String jobPerformance;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPerformance() {
        return jobPerformance;
    }

    public void setJobPerformance(String jobPerformance) {
        this.jobPerformance = jobPerformance;
    }
}
