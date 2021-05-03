package com.rishondass.loginSystem.payroll.Models;




import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="employee",schema="payroll")
public class EmployeeModel implements Serializable {

    @Id
    @Column(name="employeeid")
    public int employeeID;

    @Column(name="ssn")
    @Pattern(message="Invalid SSN Format", regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$")
    public String ssn;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(name="jobtitle")
    private String jobTitle;

    @Column(name="jobperformance")
    private String jobPerformance;

    @OneToMany(cascade=CascadeType.ALL,targetEntity = AddressModel.class, mappedBy = "employeeID",fetch = FetchType.LAZY)
    private Set<AddressModel> addresses;

    public Set<AddressModel> getAddresses() {
        return addresses;
    }

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
