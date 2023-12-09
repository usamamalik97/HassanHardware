package com.project.shop.hardware.hassanhardware.service;

import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.entity.UploadFile;

public interface FileUploadService {

	UploadFile parseAndUploadFile(MultipartFile file, boolean hasImages);

}
