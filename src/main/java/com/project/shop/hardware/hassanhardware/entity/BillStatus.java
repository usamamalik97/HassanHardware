package com.project.shop.hardware.hassanhardware.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BillStatus {

	@Id
	@Column(name = "bill_status")
	private String status;
	private String description;
	private Date createdOn;
	private Date modifiedOn;
	
	public BillStatus() {
		super();
	}
	public BillStatus(String status) {
		super();
		this.status = status;
	}
	public BillStatus(String status, String description, Date createdOn, Date modifiedOn) {
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
