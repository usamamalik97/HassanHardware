package com.project.shop.hardware.hassanhardware.bl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.project.shop.hardware.hassanhardware.entity.Brand;
import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.service.ItemService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelFileParser implements FileParser {

	List<Item> items = new ArrayList<>();
	
	List<ItemRecord> itemRecords = new ArrayList<>();
	
	@Autowired
	ItemService itemService;
	
	@Override
    public List<List<Object>> parseUploadedFile(MultipartFile file) {
        List<List<Object>> rows = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you are parsing the first sheet
            List<? extends PictureData> pictures = sheet.getWorkbook().getAllPictures();
            for(PictureData pictureData: pictures) {

				Drawing<?> drawing = sheet.getDrawingPatriarch();

				// Find the image anchor associated with the picture
				for (Shape shape : drawing) {
					if (shape instanceof Picture) {
						Picture picture = (Picture) shape;
						byte[] pictureBytes = picture.getPictureData().getData();
	                    byte[] pictureDataBytes = pictureData.getData();
	                    if (areArraysEqual(pictureBytes, pictureDataBytes)) {

							// Get the row and column of the cell containing the image
							int row = picture.getClientAnchor().getRow1();
							int column = picture.getClientAnchor().getCol1();
							System.out.println("Image: " + pictureData.toString() + " found at index RowId: " + row + " ColumnId: " + column);
						}
					}
				}
			}
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<Object> cells = new ArrayList<>();

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cells.add(getCellValue(cell, workbook));
                }
                rows.add(cells);
            }
        } catch (Exception e) {
        	System.out.println(e);
		}

        return rows;
    }

    private Object getCellValue(Cell cell, Workbook workbook) {
    	String data;
    	switch (cell.getCellType()) {
            case STRING:
            	System.out.println("Going to String");
            	data = cell.getStringCellValue();
            	System.out.println("Going to String: " + data);
                return data;
            case NUMERIC:
            	System.out.println("Going to Numeric");
            	double value = cell.getNumericCellValue();
                return value;
            case BOOLEAN:
            	System.out.println("Going to Boolean");
            	boolean flag = cell.getBooleanCellValue();
            	System.out.println("Going to Boolean: " + flag);
                return flag;
            case FORMULA:
            	System.out.println("Going to Formula");
            	data = cell.getCellFormula();
            	System.out.println("Going to Formula: " + data);
                return data;
            case BLANK:
            	System.out.println("Going to retrieve image");
            	byte[] byteData = getImageFromCell(cell, workbook);
            	System.out.println("Going to retrieve image: " + byteData);
                return byteData;
            case ERROR:
                return "ERROR";
            default:
            	System.out.println("Going to retrieve default image");
                return "";
        }
    }
    private static boolean areArraysEqual(byte[] arr1, byte[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }
    private byte[] getImageFromCell(Cell cell, Workbook workbook) {
        if (cell.getCellType() != CellType.BLANK) {
            return null; // If cell contains any value other than an image, return null
        }

        Sheet sheet = cell.getSheet();
        if (!(sheet instanceof XSSFSheet)) {
            return null; // We only support XSSF format (Excel 2007+) for images
        }

        XSSFSheet xssfSheet = (XSSFSheet) sheet;
        XSSFDrawing drawing = xssfSheet.createDrawingPatriarch();

        for (XSSFShape shape : drawing.getShapes()) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture picture = (XSSFPicture) shape;
                XSSFClientAnchor anchor = picture.getClientAnchor();
                if (anchor.getCol1() == cell.getColumnIndex() && anchor.getRow1() == cell.getRowIndex()) {
                    return picture.getPictureData().getData();
                }
            }
        }

        return null;
    }

	@Override
	public List<ItemRecord> parseUploadedDataFile(MultipartFile file) {
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming you are parsing the first sheet

			Iterator<Row> rowIterator = sheet.iterator();
			if (rowIterator.hasNext())
				rowIterator.next();
			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				if (!cellIterator.hasNext())
					return itemRecords;

				Date currentDate = new Date(new java.util.Date().getTime());
				Item item = new Item();
				ItemRecord itemRecord = new ItemRecord();
				item.setItemSize((String) getCellValue(cellIterator.next(), workbook));
				item.setItemMaterial((String) getCellValue(cellIterator.next(), workbook));
				itemRecord.setQuantity(Math.round((double) getCellValue(cellIterator.next(), workbook)));
				item.setItemWeight((double) getCellValue(cellIterator.next(), workbook));
				Brand brand = new Brand((String) getCellValue(cellIterator.next(), workbook));
				item.setBrand(brand);
				itemRecord.setTotalWeight((double) getCellValue(cellIterator.next(), workbook));
				itemRecord.setCostPrice((double) getCellValue(cellIterator.next(), workbook));
				itemRecord.setTotalCost(itemRecord.getCostPrice() * itemRecord.getQuantity());
				itemRecord.setCreatedOn(currentDate);
				itemRecord.setModifiedOn(currentDate);
				itemRecord.setPurchaseDate(currentDate);
				itemRecord.setItem(item);
				itemRecords.add(itemRecord);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return itemRecords;
    }

}
