package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

public class BillBean {

	private long billId;
	private Date billDate;
	private double totalAmount;
	private double dueAmount;
	private double paidAmount;
	private Date dueDate;
	
	private String billStatus;
	private long customerId;

	public BillBean(long billId) {
		super();
		this.billId = billId;
	}

	public BillBean() {
		super();
	}

	public BillBean(double totalAmount, double dueAmount, double paidAmount) {
		super();
		this.totalAmount = totalAmount;
		this.dueAmount = dueAmount;
		this.paidAmount = paidAmount;
	}

	public BillBean(long billId, Date billDate, double totalAmount, double dueAmount, double paidAmount, Date dueDate,
			String billStatus, long customerId) {
		super();
		this.billId = billId;
		this.billDate = billDate;
		this.totalAmount = totalAmount;
		this.dueAmount = dueAmount;
		this.paidAmount = paidAmount;
		this.dueDate = dueDate;
		this.billStatus = billStatus;
		this.customerId = customerId;
	}
	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	
}
