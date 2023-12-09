package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

public class BillPaymentBean {

	private long paymentId;
	
	private double totalDueAmount;
	private double currentDueAmount;
	private double paymentAmount;
	private double remainingAmount;
	private Date paymentDate;
	private String payeeName;
	
	private String billStatus;
	private String billId;
    
	private long transactionId;
  
	public BillPaymentBean() {
		super();
	}

	public BillPaymentBean(long paymentId) {
		super();
		this.paymentId = paymentId;
	}

	public BillPaymentBean(long paymentId, double totalDueAmount, double currentDueAmount, double paymentAmount,
			double remainingAmount, Date paymentDate, String payeeName) {
		super();
		this.paymentId = paymentId;
		this.totalDueAmount = totalDueAmount;
		this.currentDueAmount = currentDueAmount;
		this.paymentAmount = paymentAmount;
		this.remainingAmount = remainingAmount;
		this.paymentDate = paymentDate;
		this.payeeName = payeeName;
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public double getTotalDueAmount() {
		return totalDueAmount;
	}

	public void setTotalDueAmount(double totalDueAmount) {
		this.totalDueAmount = totalDueAmount;
	}

	public double getCurrentDueAmount() {
		return currentDueAmount;
	}

	public void setCurrentDueAmount(double currentDueAmount) {
		this.currentDueAmount = currentDueAmount;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double payment) {
		this.paymentAmount = payment;
	}

	public double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
}
