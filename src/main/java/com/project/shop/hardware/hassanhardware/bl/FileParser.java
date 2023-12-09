package com.project.shop.hardware.hassanhardware.bl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.entity.ItemRecord;

@Component
public interface FileParser {

	public List<List<Object>> parseUploadedFile(MultipartFile file);
	public List<ItemRecord> parseUploadedDataFile(MultipartFile file);
}
