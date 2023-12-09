package com.project.shop.hardware.hassanhardware.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.bl.ExcelFileParser;
import com.project.shop.hardware.hassanhardware.bl.FileParser;
import com.project.shop.hardware.hassanhardware.bl.TextFileParser;
import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.entity.UploadFile;
import com.project.shop.hardware.hassanhardware.repository.ItemRecordRepository;
import com.project.shop.hardware.hassanhardware.repository.ItemRepository;
import com.project.shop.hardware.hassanhardware.repository.UploadFileRepository;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	UploadFileRepository uploadFileRepository;
	
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemRecordRepository itemRecordRepository;
	
	FileParser fileParser;
	
	@Override
	public UploadFile parseAndUploadFile(MultipartFile file, boolean hasImages) {
		UploadFile uploadedFile = null;
		List<ItemRecord> itemRecords;

		try {
			FileParser fileParser = getFileParser(getFileExtension(file));
			if(fileParser != null) {
				if(hasImages) {
					fileParser.parseUploadedFile(file);					
				} else {
					itemRecords = fileParser.parseUploadedDataFile(file);
					for(ItemRecord itemRecord: itemRecords) {
						
						List<Item> items = itemRepository.findByItemSizeAndItemWeightAndItemMaterial(itemRecord.getItem().getItemSize(), itemRecord.getItem().getItemWeight(), itemRecord.getItem().getItemMaterial());
						if(items == null || items.size() == 0) {
							itemRecord.getItem().setQuantity(itemRecord.getQuantity());
							itemRecord.setItem(itemRepository.save(itemRecord.getItem()));							
						} else {
							items.get(0).setQuantity(items.get(0).getQuantity() + itemRecord.getQuantity());
							Item item = items.get(0);
							item = itemRepository.save(item);
							itemRecord.setItem(item);
							
						}
					}
					itemRecordRepository.saveAll(itemRecords);
				}
				uploadedFile = uploadFile(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadedFile;
	}

	private UploadFile uploadFile(MultipartFile file) throws IOException {
		Date currentDate = new Date(new java.util.Date().getTime());
		UploadFile uploadFile = new UploadFile();
		uploadFile.setFile(converMultipartFileToFile(file));
		uploadFile.setFileName(file.getOriginalFilename());
		uploadFile.setFileExtension(getFileExtension(file));
		uploadFile.setUploadedAt(currentDate);
		return uploadFileRepository.save(uploadFile);	
	}

	private FileParser getFileParser(String extension) {
		switch (extension) {
			case Constants.FILE_EXTENSTION_TEXT:
				return new TextFileParser();
			case Constants.FILE_EXTENSTION_EXCEL:
			case Constants.FILE_EXTENSTION_LIBRE:
				return new ExcelFileParser();
			default:
				return null;
		}
	}
	private File converMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("uploaded", file.getOriginalFilename());
        file.transferTo(tempFile);
        
        return tempFile;
	}
	
	public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int lastDotIndex = originalFileName.lastIndexOf('.');
            if (lastDotIndex != -1 && lastDotIndex < originalFileName.length() - 1) {
                return originalFileName.substring(lastDotIndex + 1);
            }
        }
        return "";
    }

}
