package com.project.shop.hardware.hassanhardware.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.entity.DataFile;

@CrossOrigin
@RestController
public class FileController {
	
	@PostMapping("/hassan-hardware/upload-file")
	public String uploadFile(@ModelAttribute DataFile dataFile) {
		return "This is your file";
	}
	
	@GetMapping("/hassan-hardware/files/upload-file")
	public String uploadFile() {
		return "This is your bill";
	}
}
