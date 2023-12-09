package com.project.shop.hardware.hassanhardware.entity;

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
public class Item {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long itemId;
	private String itemSize;
	private double itemWeight;
	private String itemMaterial;
	private long quantity;

	@JoinColumn(name = "brand")
    @ManyToOne(cascade = CascadeType.ALL)
	private Brand brand;
	
	public Item() {
		super();
	}
	
	public Item(String itemSize, double itemWeight, String itemMaterial) {
		super();
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
	}

	public Item(long itemId, String itemSize, double itemWeight, String itemMaterial) {
		super();
		this.itemId = itemId;
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
	}

	public Item(String itemSize, double itemWeight, String itemMaterial, long quantity) {
		super();
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
		this.quantity = quantity;
	}

	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	public double getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(double itemWeight) {
		this.itemWeight = itemWeight;
	}
	public String getItemMaterial() {
		return itemMaterial;
	}
	public void setItemMaterial(String itemMaterial) {
		this.itemMaterial = itemMaterial;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
}
