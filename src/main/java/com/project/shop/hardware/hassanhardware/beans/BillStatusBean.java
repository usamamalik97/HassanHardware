package com.project.shop.hardware.hassanhardware.beans;

import java.sql.Date;

public class BillStatusBean {

	private String status;
	private String description;
	private Date createdOn;
	private Date modifiedOn;
		
	public BillStatusBean() {
		super();
	}
	public BillStatusBean(String status) {
		super();
		this.status = status;
	}
	public BillStatusBean(String status, String description, Date createdOn, Date modifiedOn) {
		super();
		this.status = status;
		this.description = description;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
