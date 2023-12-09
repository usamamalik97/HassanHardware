package com.project.shop.hardware.hassanhardware.beans;

import com.project.shop.hardware.hassanhardware.entity.Customer;

public class CustomerBean {

	private long customerId;
	private String customerName;
	private String phoneNumber;
	private String emailId;
	
    private long shopId;
    	
	public CustomerBean() {
		super();
	}
	
	public CustomerBean(long customerId) {
		super();
		this.customerId = customerId;
	}

	public CustomerBean(long customerId, String customerName, String phoneNumber, String emailId) {
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

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public Customer getEntityObject() {
		Customer customer = new Customer();
		customer.setCustomerName(this.customerName);
		customer.setEmailId(this.emailId);
		customer.setPhoneNumber(this.phoneNumber);
		return customer;
	}

}
