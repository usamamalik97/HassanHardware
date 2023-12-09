package com.project.shop.hardware.hassanhardware.beans;

import java.io.File;
import java.sql.Date;

public class UploadFile {

	private long fileId;
	
	private File file;
	
	private String fileName;
	private String fileExtension;
	private Date uploadedAt;
	
	public UploadFile(File file) {
		super();
		this.file = file;
	}
	public UploadFile(long fileId) {
		super();
		this.fileId = fileId;
	}
	public UploadFile() {
		super();
	}
	public UploadFile(long fileId, File file) {
		super();
		this.fileId = fileId;
		this.file = file;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public Date getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	
}
