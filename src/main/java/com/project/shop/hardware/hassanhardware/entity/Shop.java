package com.project.shop.hardware.hassanhardware.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
public class Shop {
	@Id
	@Column(name = "shop_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long shopId;
	private String shopName;
	private String phoneNumber;
	private String emailId;
	private String address;

    @JsonIgnoreProperties({"shop"}) // Exclude the "bill" field from serialization in Customer
	@JsonBackReference
    @JoinColumn(name = "shop_type", foreignKey = @ForeignKey(name = "fk_shop_type", value = ConstraintMode.CONSTRAINT))
	@ManyToOne(cascade = CascadeType.ALL)
    private ShopType shopType;
	
	public Shop(long shopId) {
		super();
		this.shopId = shopId;
	}
	public Shop(long shopId, String shopName, String phoneNumber, String emailId, String address) {
		super();
		this.shopId = shopId;
		this.shopName = shopName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.address = address;
	}
	public Shop(String shopName, String phoneNumber, String emailId, String address) {
		super();
		this.shopName = shopName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.address = address;
	}
	public Shop() {
		super();
	}
	public ShopType getShopType() {
		return shopType;
	}
	public void setShopType(ShopType shopType) {
		this.shopType = shopType;
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
	
}
