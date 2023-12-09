package com.project.shop.hardware.hassanhardware.bl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.entity.ItemRecord;

@Component
public class TextFileParser implements FileParser {

	@Override
	public List<List<Object>> parseUploadedFile(MultipartFile file) {
		return null;
	}

	@Override
	public List<ItemRecord> parseUploadedDataFile(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
