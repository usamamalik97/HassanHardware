package com.project.shop.hardware.hassanhardware.service;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class PDFGenerator {
    public byte[] createPdf() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello, this is a PDF document created with Apache PDFBox in Spring Boot!");
                contentStream.endText();
            }
            document.save(new FileOutputStream(new File("E:\\PDF\\firstPDF.pdf")));
/*
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);*/
            return null;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }/*
    private String generateBillContent(List<SoldItems> items) {
        // Sample bill content
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        StringBuilder billContent = new StringBuilder();
        billContent.append("---- Demo Bill ----\n\n");
        billContent.append("Bill Number: 123456\n");
        billContent.append("Date: ").append(currentDate).append("\n\n");
        billContent.append("Item\t\tQuantity\tPrice\n");
        billContent.append("--------------------------------------\n");
        for (SoldItems item : items) {
            billContent.append(item.getDiscEntry().getDisc().getShop().getShopName()).append("\t\t").append(item.getQuantity()).append("\t\t$").append(item.getPrice()).append("\n");
        }
        billContent.append("--------------------------------------\n");
        billContent.append("Total:\t\t\t\t$").append(calculateTotalAmount(items)).append("\n\n");
        billContent.append("Thank you for your purchase!");

        return billContent.toString();
    }*/
    public byte[] generateBillPdf(Bill bill) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bill ID: " + bill.getBillId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Customer Name: " + bill.getCustomer().getCustomerName());
                // Add more bill details as needed...
                contentStream.endText();
            }
            document.save(new FileOutputStream(new File("E:\\PDF\\firstPDF.pdf")));

/*            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();*/
        }
        return null;
    }
}
