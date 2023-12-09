package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

import com.project.shop.hardware.hassanhardware.entity.Cheque;

public class ChequeBean {
	private long chequeId;
	private Date dueDate;
	private double amount;
	private String bank;
	private String chequeCashed;
	private String chequeNo;
	private String approved;
	private long customer;
	
	public ChequeBean() {
		super();
	}
	
	public ChequeBean(long chequeId, Date dueDate, double amount, String bank, String chequeCashed, String chequeNo) {
		super();
		this.chequeId = chequeId;
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = chequeCashed;
		this.chequeNo = chequeNo;
	}

	public ChequeBean(Date dueDate, double amount, String bank, String chequeCashed, String chequeNo) {
		super();
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = chequeCashed;
		this.chequeNo = chequeNo;
	}

	public ChequeBean(Date dueDate, double amount, String bank) {
		super();
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
	}

	public ChequeBean(long chequeId, Date dueDate, double amount, String bank, String chequeCashed, String chequeNo,
			String approved) {
		super();
		this.chequeId = chequeId;
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = chequeCashed;
		this.chequeNo = chequeNo;
		this.approved = approved;
	}

	public ChequeBean(long chequeId, Date dueDate, double amount, String bank, String checkCashed) {
		super();
		this.chequeId = chequeId;
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = checkCashed;
	}

	public long getChequeId() {
		return chequeId;
	}
	public void setChequeId(long chequeId) {
		this.chequeId = chequeId;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public long getCustomer() {
		return customer;
	}
	public void setCustomer(long customer) {
		this.customer = customer;
	}
	public Cheque getEntityObject() {
		return new Cheque(chequeId,  dueDate, amount, bank, chequeCashed, chequeNo);
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getChequeCashed() {
		return chequeCashed;
	}

	public void setChequeCashed(String chequeCashed) {
		this.chequeCashed = chequeCashed;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
}
