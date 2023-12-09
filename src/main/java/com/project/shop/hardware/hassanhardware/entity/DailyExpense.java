package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DailyExpense {
	

	@Id
	@Column(name = "daily_expense_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long dailyExpenseId;
	private Date date;
	private double amount;
	private String comment;

	@JoinColumn(name = "expense_type")
    @ManyToOne(cascade = CascadeType.ALL)
	private ExpenseType expenseType;

	
	public DailyExpense() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyExpense(Date date, double amount, String comment) {
		super();
		this.date = date;
		this.amount = amount;
		this.comment = comment;
	}

	public DailyExpense(long dailyExpenseId, Date date, double amount, String comment) {
		super();
		this.dailyExpenseId = dailyExpenseId;
		this.date = date;
		this.amount = amount;
		this.comment = comment;
	}

	public DailyExpense(long dailyExpenseId, Date date, double amount, String comment, ExpenseType expenseType) {
		super();
		this.dailyExpenseId = dailyExpenseId;
		this.date = date;
		this.amount = amount;
		this.comment = comment;
		this.expenseType = expenseType;
	}

	public DailyExpense(Date date, double amount, String comment, ExpenseType expenseType) {
		super();
		this.date = date;
		this.amount = amount;
		this.comment = comment;
		this.expenseType = expenseType;
	}

	public long getDailyExpenseId() {
		return dailyExpenseId;
	}

	public void setDailyExpenseId(long dailyExpenseId) {
		this.dailyExpenseId = dailyExpenseId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	
}
