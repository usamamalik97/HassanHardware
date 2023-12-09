package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

import com.project.shop.hardware.hassanhardware.entity.DailyExpense;

public class DailyExpenseBean {

	private long dailyExpenseId;
	private Date date;
	private double amount;
	private String comment;

	private String expenseType;

	public DailyExpenseBean(long dailyExpenseId, Date date, double amount, String comment, String expenseType) {
		super();
		this.dailyExpenseId = dailyExpenseId;
		this.date = date;
		this.amount = amount;
		this.comment = comment;
		this.expenseType = expenseType;
	}

	public DailyExpenseBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyExpenseBean(long dailyExpenseId, Date date, double amount, String comment) {
		super();
		this.dailyExpenseId = dailyExpenseId;
		this.date = date;
		this.amount = amount;
		this.comment = comment;
	}

	public DailyExpenseBean(Date date, double amount, String comment) {
		super();
		this.date = date;
		this.amount = amount;
		this.comment = comment;
	}

	public DailyExpenseBean(Date date, double amount) {
		super();
		this.date = date;
		this.amount = amount;
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

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public DailyExpense getEntityObject() {
		return new DailyExpense(date, amount, comment);
	}
	
	
}
