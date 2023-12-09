package com.project.shop.hardware.hassanhardware.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.UploadFile;
import com.project.shop.hardware.hassanhardware.service.FileUploadService;
import com.project.shop.hardware.hassanhardware.service.PDFGenerator;

@CrossOrigin
@RestController
@RequestMapping("/hassan-hardware/file")
public class FileUploadController {
	
	@Autowired
	FileUploadService fileUploadService;
	
	@PostMapping("/upload")
	public UploadFile saveFile(@RequestParam("file") MultipartFile file) {
		return fileUploadService.parseAndUploadFile(file, Boolean.FALSE);
	}
	@PostMapping("/upload/PDF")
	public byte[] savePDFFile(@RequestParam("file") MultipartFile file) {
		PDFGenerator pdfGenerator = new PDFGenerator();
		Bill bill = new Bill();
		bill.setBillId(1001);
		bill.setCustomer(new Customer());
		bill.getCustomer().setCustomerName("USAMA");
		try {
			return pdfGenerator.generateBillPdf(bill);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		//return pdfGenerator.createPdf();
	}
	
}
