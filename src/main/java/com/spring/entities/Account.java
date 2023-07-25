package com.spring.entities;

import jakarta.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {

	private String name;
	@Id
	@Column(unique = true)
	@NotNull(message = "Email is required")
	private String email;
	@Min(value=0,message="Monthly salary must be greater than or equal to 0")
	private double monthlySalary;
	private double monthlyExpenses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getmonthlySalary() {
		return monthlySalary;
	}

	public void setmonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public double getmonthlyExpenses() {
		return monthlyExpenses;
	}

	public void setmonthlyExpenses(double monthlyExpenses) {
		this.monthlyExpenses = monthlyExpenses;
	}
}
