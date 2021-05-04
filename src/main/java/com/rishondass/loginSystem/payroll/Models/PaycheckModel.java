package main.java.com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;

@Entity
@Table(name="Paycheck",schema="payroll")

public class PaycheckModel {
	
	@Column(name = "date")
	private String date;
	
	@Column(name="ssn")
	private String ssn;

	@Column(name="income")
	private int income;

	@Column(name="taxDeductions")
	private int taxDeductions;

	@Column(name="deduction401k")
	private int deduction401k;

	@Column(name="insurancePremiums")
	private String insurancePremiums;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getTaxDeductions() {
		return taxDeductions;
	}

	public void setTaxDeductions(int taxDeductions) {
		this.taxDeductions = taxDeductions;
	}

	public int getDeduction401k() {
		return deduction401k;
	}

	public void setDeduction401k(int deduction401k) {
		this.deduction401k = deduction401k;
	}

	public String getInsurancePremiums() {
		return insurancePremiums;
	}

	public void setInsurancePremiums(String insurancePremiums) {
		this.insurancePremiums = insurancePremiums;
	}
	
}
