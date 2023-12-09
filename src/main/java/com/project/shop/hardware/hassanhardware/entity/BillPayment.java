package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BillPayment {

	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long paymentId;
	
	private double totalDueAmount;
	private double currentDueAmount;
	private double paymentAmount;
	private double remainingAmount;
	private Date paymentDate;
	private String payeeName;
	
    @JsonIgnoreProperties({"billPayment"}) // Exclude the "bill" field from serialization in Customer
	@JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_status", foreignKey = @ForeignKey(name = "FK_bill_payment_bill_status", value = ConstraintMode.CONSTRAINT))
	private BillStatus billStatus;

    @JsonIgnoreProperties({"billPayment"}) // Exclude the "bill" field from serialization in Customer
    @JsonBackReference
	@JoinColumn(name = "bill_id", foreignKey = @ForeignKey(name = "FK_bill_payment_bill", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Bill bill;
    

    @JsonIgnoreProperties({"billPayment"}) // Exclude the "bill" field from serialization in Customer
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trans_id")
	private TransactionInfo transactionInfo;
  
	public BillPayment() {
		super();
	}

	public BillPayment(long paymentId) {
		super();
		this.paymentId = paymentId;
	}

	public BillPayment(long paymentId, double totalDueAmount, double currentDueAmount, double paymentAmount,
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

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
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

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public TransactionInfo getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(TransactionInfo transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
	
}
