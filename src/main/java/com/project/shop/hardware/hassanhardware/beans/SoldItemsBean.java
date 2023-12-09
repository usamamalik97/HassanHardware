package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

import com.project.shop.hardware.hassanhardware.entity.SoldItems;

public class SoldItemsBean {

	private long id;
	private double sellPrice;
	private Date createdOn;
	private Date modifiedOn;
	private Date purchaseDate;
	private long quantitySold;
	
	private Long itemRecordId;
	
	private Long billId;

	
	public SoldItemsBean() {
		super();
	}

	public SoldItemsBean(long id) {
		super();
		this.id = id;
	}

	public SoldItemsBean(long id, double sellPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantitySold) {
		super();
		this.id = id;
		this.sellPrice = sellPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantitySold = quantitySold;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
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

	public long getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(long quantitySold) {
		this.quantitySold = quantitySold;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getItemRecordId() {
		return itemRecordId;
	}

	public void setItemRecordId(Long itemRecordId) {
		this.itemRecordId = itemRecordId;
	}

	public SoldItems getEntityObject() {
		Date currentDate = new Date(new java.util.Date().getTime());
		return new SoldItems(sellPrice, currentDate, currentDate, currentDate, quantitySold);
	}
}
