package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

public class ShopTypeBean {

	private String type;
	private String description;
	private Date createdOn;
	private Date modifiedOn;
	
	public ShopTypeBean() {
		super();
	}
	public ShopTypeBean(String type) {
		super();
		this.type = type;
	}
	
	public ShopTypeBean(String type, String description, Date createdOn, Date modifiedOn) {
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
