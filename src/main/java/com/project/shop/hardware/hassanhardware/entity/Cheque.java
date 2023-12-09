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
public class Cheque {

	@Id
	@Column(name = "cheque_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long chequeId;
	private Date dueDate;
	private double amount;
	private String bank;
	private String chequeCashed;
	private String chequeNo;
	private String approved;

	@JoinColumn(name = "customer_id")
    @ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;

	@JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	public Cheque() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Cheque(long chequeId, Date dueDate, double amount, String bank, String chequeCashed, String chequeNo) {
		super();
		this.chequeId = chequeId;
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = chequeCashed;
		this.chequeNo = chequeNo;
	}

	public Cheque(Date dueDate, double amount, String bank) {
		super();
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
	}

	public Cheque(long chequeId, Date dueDate, double amount, String bank, String chequeCashed) {
		super();
		this.chequeId = chequeId;
		this.dueDate = dueDate;
		this.amount = amount;
		this.bank = bank;
		this.chequeCashed = chequeCashed;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getChequeCashed() {
		return chequeCashed;
	}

	public void setChequeCashed(String chequeCashed) {
		this.chequeCashed = chequeCashed;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
