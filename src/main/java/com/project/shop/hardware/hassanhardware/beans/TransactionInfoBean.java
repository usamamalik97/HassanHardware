package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;

public class TransactionInfoBean {
	
	private long transId;
	
	private double paymentAmount;
	private double adjustedAmount;
	private Date paymentDate;
	private String payeeName;
	private String approved;

	private String transactionStatus;
	
	private long customerId;
	
	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
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

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public double getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public TransactionInfo getEntityObject() {
		return new TransactionInfo(paymentAmount, adjustedAmount, paymentDate, payeeName);
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
}
