package com.project.shop.hardware.hassanhardware.beans;

import com.project.shop.hardware.hassanhardware.entity.Item;

public class ItemBean {
	private long itemId;
	private String itemSize;
	private double itemWeight;
	private String itemMaterial;
	private long quantity;
	private double costPrice;

	public ItemBean() {
		super();
	}
	
	public ItemBean(String itemSize, double itemWeight, String itemMaterial) {
		super();
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
	}

	public ItemBean(String itemSize, double itemWeight, String itemMaterial, long quantity, double costPrice) {
		super();
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
		this.quantity = quantity;
		this.costPrice = costPrice;
	}

	public ItemBean(long itemId, String itemSize, double itemWeight, String itemMaterial) {
		super();
		this.itemId = itemId;
		this.itemSize = itemSize;
		this.itemWeight = itemWeight;
		this.itemMaterial = itemMaterial;
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
	public Item getEntityObject() {
		Item item = new Item();
		item.setItemSize(this.itemSize);
		item.setItemMaterial(this.itemMaterial);
		item.setItemWeight(this.itemWeight);
		return item;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	
}
