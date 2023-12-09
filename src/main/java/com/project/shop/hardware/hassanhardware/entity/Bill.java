package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

@Entity
@Table(name = "bill")
public class Bill {

	@Id
	@Column(name = "bill_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long billId;
	private Date billDate;
	private double totalAmount;
	private double dueAmount;
	private double paidAmount;
	private Date dueDate;
	
    @JsonIgnoreProperties({"bill"}) // Exclude the "bill" field from serialization in Customer
	@JoinColumn(name = "bill_status", foreignKey = @ForeignKey(name = "FK_bill_bill_status", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL)
	private BillStatus billStatus;
	
    @JsonIgnoreProperties({"bill"}) // Exclude the "bill" field from serialization in Customer
	@JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_bill_customer", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Bill(long billId, Date billDate, double totalAmount, double dueAmount, double paidAmount, Date dueDate) {
		super();
		this.billId = billId;
		this.billDate = billDate;
		this.totalAmount = totalAmount;
		this.dueAmount = dueAmount;
		this.paidAmount = paidAmount;
		this.dueDate = dueDate;
	}
	
	public Bill(long billId) {
		super();
		this.billId = billId;
	}

	public Bill() {
		super();
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}


	/*public DataFile getBill() {
		return bill;
	}

	public void setBill(DataFile bill) {
		this.bill = bill;
	}
*/
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

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
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

	/*public List<SoldItems> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(List<SoldItems> soldItems) {
		this.soldItems = soldItems;
	}*/
	
}
