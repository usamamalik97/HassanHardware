package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

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
public class SoldItems {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private double sellPrice;
	private Date createdOn;
	private Date modifiedOn;
	private Date purchaseDate;
	private long quantitySold;
	
    @JsonIgnoreProperties({"soldItems"}) // Exclude the "bill" field from serialization in Customer
	@JsonBackReference
	@JoinColumn(name = "bill_id", foreignKey = @ForeignKey(name = "FK_sold_items_bill", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL)
	private Bill bill;


    @JsonIgnoreProperties({"soldItems"}) // Exclude the "bill" field from serialization in Customer
	@JsonBackReference
	@JoinColumn(name = "item_record", foreignKey = @ForeignKey(name = "FK_sold_items_item_record", value = ConstraintMode.CONSTRAINT))
    @ManyToOne(cascade = CascadeType.ALL)
	private ItemRecord itemRecord;
	
	public SoldItems() {
		super();
	}

	public SoldItems(long id) {
		super();
		this.id = id;
	}

	public SoldItems(long id, double sellPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantitySold) {
		super();
		this.id = id;
		this.sellPrice = sellPrice;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.purchaseDate = purchaseDate;
		this.quantitySold = quantitySold;
	}

	public SoldItems(double sellPrice, Date createdOn, Date modifiedOn, Date purchaseDate, long quantitySold) {
		super();
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

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public ItemRecord getItemRecord() {
		return itemRecord;
	}

	public void setItemRecord(ItemRecord itemRecord) {
		this.itemRecord = itemRecord;
	}
	
}
