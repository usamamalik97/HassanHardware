package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

import com.project.shop.hardware.hassanhardware.entity.ItemRecord;

public class ItemRecordBean {

	private long id;
	private double costPrice;
	private Date createdOn;
	private Date modifiedOn;
	private Date purchaseDate;
	private long quantity;
	private long quantitySold;
	private long quantityRemaining;
	private double totalWeight;
	private double totalCost;
	
    private long itemId;

	public ItemRecordBean() {
		super();
	}

	public ItemRecordBean(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
			long quantitySold, long quantityRemaining) {
		super();
		this.costPrice = costPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
		this.quantitySold = quantitySold;
		this.quantityRemaining = quantityRemaining;
	}

	public ItemRecordBean(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
			long quantitySold, long quantityRemaining, double totalWeight, double totalCost) {
		super();
		this.costPrice = costPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
		this.quantitySold = quantitySold;
		this.quantityRemaining = quantityRemaining;
		this.totalWeight = totalWeight;
		this.totalCost = totalCost;
	}

	public ItemRecordBean(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
			long quantitySold, long quantityRemaining, double totalWeight) {
		super();
		this.costPrice = costPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
		this.quantitySold = quantitySold;
		this.quantityRemaining = quantityRemaining;
		this.totalWeight = totalWeight;
	}

	public ItemRecordBean(long id, double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
			long quantitySold, long quantityRemaining, long itemId) {
		super();
		this.id = id;
		this.costPrice = costPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
		this.quantitySold = quantitySold;
		this.quantityRemaining = quantityRemaining;
		this.itemId = itemId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(long quantitySold) {
		this.quantitySold = quantitySold;
	}

	public long getQuantityRemaining() {
		return quantityRemaining;
	}

	public void setQuantityRemaining(long quantityRemaining) {
		this.quantityRemaining = quantityRemaining;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public ItemRecord getEntityObject() {
		
		ItemRecord itemRecord = new ItemRecord();
		itemRecord.setCostPrice(this.costPrice);
		itemRecord.setPurchaseDate(this.purchaseDate);
		itemRecord.setQuantity(this.quantity);
		itemRecord.setQuantitySold(this.quantitySold);
		itemRecord.setQuantityRemaining(this.quantityRemaining);
		itemRecord.setTotalWeight(this.totalWeight);
		itemRecord.setTotalCost(this.totalCost);
		return itemRecord;
	}
}
