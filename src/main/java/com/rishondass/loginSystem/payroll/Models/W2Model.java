package main.java.com.rishondass.loginSystem.payroll.Models;

import javax.persistence.*;

@Entity
@Table(name="W2",schema="payroll")

public class W2Model {
	
	@Column(name = "date")
	private String date;
	
	@Column(name="ssn")
	private String ssn;

	@Column(name="yearlyIncome")
	private int yearlyIncome;

	@Column(name="deduction")
	private int deduction;

	@Column(name="extraEarnings")
	private int extraEarnings;

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

	public int getYearlyIncome() {
		return yearlyIncome;
	}

	public void setYearlyIncome(int yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public int getDeduction() {
		return deduction;
	}

	public void setDeduction(int deduction) {
		this.deduction = deduction;
	}

	public int getExtraEarnings() {
		return extraEarnings;
	}

	public void setExtraEarnings(int extraEarnings) {
		this.extraEarnings = extraEarnings;
	}
	
}
