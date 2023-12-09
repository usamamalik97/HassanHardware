package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ShopType {

	@Id
	@Column(name = "shop_type")
	private String type;
	private String description;
	private Date createdOn;
	private Date modifiedOn;
	
	public ShopType() {
		super();
	}
	public ShopType(String type) {
		super();
		this.type = type;
	}
	
	public ShopType(String type, String description, Date createdOn, Date modifiedOn) {
		super();
		this.type = type;
		this.description = description;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
}
