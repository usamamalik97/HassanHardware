package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Brand {
	@Id
	private String brandName;
	private Date createdOn;
	private Date modifiedOn;
	
	public Brand() {
		super();
	}

	public Brand(String brandName) {
		super();
		this.brandName = brandName;
	}


	public Brand(String brandName, Date createdOn, Date modifiedOn) {
		super();
		this.brandName = brandName;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
