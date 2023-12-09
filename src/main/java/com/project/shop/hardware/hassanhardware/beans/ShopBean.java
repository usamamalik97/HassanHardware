package com.project.shop.hardware.hassanhardware.beans;

import com.project.shop.hardware.hassanhardware.entity.Shop;

public class ShopBean {
	private long shopId;
	private String shopName;
	private String phoneNumber;
	private String emailId;
	private String address;

    private String shopType;
	
	public ShopBean(long shopId) {
		super();
		this.shopId = shopId;
	}
	public ShopBean(long shopId, String shopName, String phoneNumber, String emailId, String address) {
		super();
		this.shopId = shopId;
		this.shopName = shopName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.address = address;
	}
	public ShopBean(String shopName, String phoneNumber, String emailId, String address) {
		super();
		this.shopName = shopName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.address = address;
	}
	public ShopBean() {
		super();
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public Shop getEntityObject() {
		Shop shop = new Shop();
		shop.setShopName(this.shopName);
		shop.setEmailId(this.emailId);
		shop.setPhoneNumber(this.phoneNumber);
		shop.setAddress(this.address);
		return shop;
	}
	
}
