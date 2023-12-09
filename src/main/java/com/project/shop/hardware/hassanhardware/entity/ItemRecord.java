package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

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
public class ItemRecord {

	@Id
	@Column(name = "item_record_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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

	@ManyToOne
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_item_record_item", value = ConstraintMode.CONSTRAINT))
    private Item item;
	
	public ItemRecord() {
		super();
	}

	public ItemRecord(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
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

	public ItemRecord(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
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

	public ItemRecord(double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
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

	public ItemRecord(long id, double costPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantity,
			long quantitySold, long quantityRemaining) {
		super();
		this.id = id;
		this.costPrice = costPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantity = quantity;
		this.quantitySold = quantitySold;
		this.quantityRemaining = quantityRemaining;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

}
