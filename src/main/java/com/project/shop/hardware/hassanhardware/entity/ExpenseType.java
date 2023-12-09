package com.project.shop.hardware.hassanhardware.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ExpenseType {

	@Id
	private String expenseType;
	private String description;
	
	public ExpenseType() {
		super();
	}
	public ExpenseType(String expenseType) {
		super();
		this.expenseType = expenseType;
	}
	public ExpenseType(String expenseType, String description) {
		super();
		this.expenseType = expenseType;
		this.description = description;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
