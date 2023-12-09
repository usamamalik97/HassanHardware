package com.project.shop.hardware.hassanhardware.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Transient;

@Entity
public class Customer {

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long customerId;
	private String customerName;
	private String phoneNumber;
	private String emailId;
	
	@Transient
	private String shopName;

	@Transient
	private double holdAmount;
	
    @JsonIgnoreProperties({"customer"}) // Exclude the "bill" field from serialization in Customer
    @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(name = "FK_customer_shop", value = ConstraintMode.CONSTRAINT))
	@ManyToOne(cascade = CascadeType.ALL)
    private Shop shop;

	public Customer() {
		super();
	}
	
	public Customer(long customerId) {
		super();
		this.customerId = customerId;
	}

	public Customer(long customerId, String customerName, String phoneNumber, String emailId) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public double getHoldAmount() {
		return holdAmount;
	}

	public void setHoldAmount(double holdAmount) {
		this.holdAmount = holdAmount;
	}
	
}
