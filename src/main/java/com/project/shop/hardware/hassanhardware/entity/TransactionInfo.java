package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class TransactionInfo {
	@Id
	@Column(name = "trans_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long transId;
	
	private double paymentAmount;
	private double adjustedAmount;
	private Date paymentDate;
	private String payeeName;
	private String approved;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_status", foreignKey = @ForeignKey(name = "FK_transaction_info_bill_status", value = ConstraintMode.CONSTRAINT))
	private BillStatus billStatus;
	
	@JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_tranasaction_info_customer", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
    @JoinColumn(name = "cheque_id")
    @OneToOne(cascade = CascadeType.ALL)
	private Cheque cheque;
	

	@JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	public TransactionInfo() {
		super();
	}

	public TransactionInfo(long transId, double paymentAmount, double adjustedAmount, Date paymentDate,
			String payeeName, String approved, BillStatus billStatus) {
		super();
		this.transId = transId;
		this.paymentAmount = paymentAmount;
		this.adjustedAmount = adjustedAmount;
		this.paymentDate = paymentDate;
		this.payeeName = payeeName;
		this.approved = approved;
		this.billStatus = billStatus;
	}

	public TransactionInfo(double paymentAmount, double adjustedAmount, Date paymentDate, String payeeName) {
		super();
		this.paymentAmount = paymentAmount;
		this.adjustedAmount = adjustedAmount;
		this.paymentDate = paymentDate;
		this.payeeName = payeeName;
	}

	public TransactionInfo(long transId, double paymentAmount, double adjustedAmount, Date paymentDate,
			String payeeName, BillStatus billStatus) {
		super();
		this.transId = transId;
		this.paymentAmount = paymentAmount;
		this.adjustedAmount = adjustedAmount;
		this.paymentDate = paymentDate;
		this.payeeName = payeeName;
		this.billStatus = billStatus;
	}

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

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
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
