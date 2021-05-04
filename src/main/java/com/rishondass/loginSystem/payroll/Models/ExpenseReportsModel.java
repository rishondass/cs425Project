package com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;

@Entity
@Table(name="ExpenseReports",schema="payroll")

public class ExpenseReportsModel {
	@Id
	@Column(name = "date")
	private String date;
	
	@Column(name="employeeID")
	private int employeeID;

	@Column(name="wages")
	private int wages;

	@Column(name="bonus")
	private int bonus;

	@Column(name="contribution401k")
	private int contribution401k;

	@Column(name="contributionSSN")
	private int contributionSSN;

	@Column(name="contributionInsurance")
	private int contributionInsurance;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public int getWages() {
		return wages;
	}

	public void setWages(int wages) {
		this.wages = wages;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getContribution401k() {
		return contribution401k;
	}

	public void setContribution401k(int contribution401k) {
		this.contribution401k = contribution401k;
	}

	public int getContributionSSN() {
		return contributionSSN;
	}

	public void setContributionSSN(int contributionSSN) {
		this.contributionSSN = contributionSSN;
	}

	public int getContributionInsurance() {
		return contributionInsurance;
	}

	public void setContributionInsurance(int contributionInsurance) {
		this.contributionInsurance = contributionInsurance;
	}
	
	

}