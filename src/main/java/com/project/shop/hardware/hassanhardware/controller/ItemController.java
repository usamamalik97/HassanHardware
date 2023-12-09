package com.project.shop.hardware.hassanhardware.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.shop.hardware.hassanhardware.beans.ItemBean;
import com.project.shop.hardware.hassanhardware.beans.ItemRecordBean;
import com.project.shop.hardware.hassanhardware.beans.SoldItemsBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;
import com.project.shop.hardware.hassanhardware.service.ItemService;
import com.project.shop.hardware.hassanhardware.service.SoldItemsService;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;

@CrossOrigin
@RestController
@RequestMapping("/hassan-hardware/items")
public class ItemController {
	@Autowired
	private RequestMapper mapper;
	
	@Autowired
	ItemService itemService;

	@Autowired
	SoldItemsService soldItemsService;
	
	@PostMapping("/add")
	public Item saveItem(@RequestParam("Item") String item, @RequestParam("ItemRecord") String itemRecord) {
		ItemBean itemBean = mapper.mapValues(item, ItemBean.class);
		ItemRecordBean itemRecordBean = mapper.mapValues(itemRecord, ItemRecordBean.class);

		Item dbItem = itemService.saveItem(itemBean.getEntityObject(), itemRecordBean.getQuantity());
		ItemRecord dbItemRecord = itemRecordBean.getEntityObject();
		dbItemRecord.setItem(dbItem);
		itemService.saveItemRecord(dbItemRecord);
		return dbItem;
	}
	@PostMapping("/addAll")
	public Item saveItems(@RequestParam("Items") String items, @RequestParam("Cost") String cost) {
	    String cleanCost = cost.replaceAll("\"", "");

		double perKgCostAmount = cleanCost != null ? Double.valueOf(cleanCost) : 0.0;
		Date currentDate = new Date(new java.util.Date().getTime());

		List<ItemBean> itemBeans = mapper.mapValuesToList(items, new TypeReference<List<ItemBean>>() {});
		if(itemBeans != null) {
			for(ItemBean itemBean: itemBeans) {
				Item dbItem = itemService.saveItem(itemBean.getEntityObject(), itemBean.getQuantity());
				ItemRecord itemRecord = new ItemRecord();
				itemRecord.setCostPrice(itemBean.getCostPrice() + (itemBean.getItemWeight() / 1000 * perKgCostAmount));
				itemRecord.setQuantity(itemBean.getQuantity());
				itemRecord.setPurchaseDate(currentDate);
				itemRecord.setQuantitySold(0);
				itemRecord.setQuantityRemaining(itemBean.getQuantity());
				itemRecord.setTotalWeight(itemBean.getItemWeight() * itemBean.getQuantity());
				itemRecord.setTotalCost(itemBean.getQuantity() * itemRecord.getCostPrice());
				itemRecord.setItem(dbItem);
				itemService.saveItemRecord(itemRecord);
			}
		}
		return null;
	}
	@GetMapping("/")
	public List<Item> getItems() {
		List<Item> itemList = itemService.getItems();
		return itemList;
	}

	@GetMapping("/{itemId}/")
	public ResponseEntity<Map<String, Object>> getItem(@PathVariable("itemId") long itemId) {
		List<ItemRecord> itemRecords = itemService.getItemRecords(itemId);
		Item item = itemService.getItem(itemId);
		 Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("item", item);
        responseMap.put("itemRecords", itemRecords);
        responseMap.put("lastMonthStats", getSaleStatsForDays(itemId, 31));
        responseMap.put("sixMonthStats", getSaleStatsForDays(itemId, 184));
        responseMap.put("lastYearStats", getSaleStatsForDays(itemId, 366));
        responseMap.put("costProfitStats", getProfitStats(itemRecords));

        return ResponseEntity.ok(responseMap);
	}
	@GetMapping("/report/{date}/")
	public ResponseEntity<Map<String, Object>> getItem(@PathVariable("date") Date purchaseDate) {
		List<ItemRecord> itemRecords = itemService.getItemRecordsWithDate(purchaseDate);
		 Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("itemRecords", itemRecords);
        responseMap.put("totalWeight", getTotalWeight(itemRecords));
        responseMap.put("totalCost", getTotalCost(itemRecords));

        return ResponseEntity.ok(responseMap);
	}
	private Object getTotalWeight(List<ItemRecord> itemRecords) {
		double totalWeight = 0.0;
		if(itemRecords != null) {
			for(ItemRecord itemRecord : itemRecords) {
				totalWeight += itemRecord.getTotalWeight();
			}
		}
		return totalWeight;
	}
	private Object getTotalCost(List<ItemRecord> itemRecords) {
		double totalCost = 0.0;
		if(itemRecords != null) {
			for(ItemRecord itemRecord : itemRecords) {
				totalCost += itemRecord.getTotalCost();
			}
		}
		return totalCost;
	}
	private Object getProfitStats(List<ItemRecord> itemRecords) {
		double totalCost = 0.0;
		double totalSale = 0.0;
		List<SoldItems> soldItems;
		for(ItemRecord itemRecord: itemRecords) {
			soldItems = soldItemsService.getSoldItemsForItemRecord(itemRecord.getId());
			for(SoldItems soldItem: soldItems) {
				totalSale += (soldItem.getSellPrice() * soldItem.getQuantitySold());
				totalCost += (itemRecord.getCostPrice() * soldItem.getQuantitySold());
			}
		}
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Cost");
		paymentStatsTemp.put("value", String.valueOf(totalCost));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Profit");
		paymentStatsTemp.put("value", String.valueOf(totalSale - totalCost));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	private Object getSaleStatsForDays(long itemId, int days) {
		Date currentDate = new Date(new java.util.Date().getTime());
		currentDate = CommonUtils.convertToSqlDate(CommonUtils.addInDate(currentDate, -days, Calendar.DAY_OF_MONTH));
		List<SoldItems> soldItems;
		List<ItemRecord> itemRecords;
		long totalQuantity = 0;
		long totalSold = 0;
		itemRecords = itemService.getItemRecordsAfterDate(itemId, currentDate);
		for(ItemRecord itemRecord: itemRecords) {
			totalQuantity += itemRecord.getQuantity();
		}
		soldItems = soldItemsService.getSoldItemsForItemAfter(itemId, currentDate);
		for(SoldItems soldItem: soldItems) {
			totalSold += soldItem.getQuantitySold();
		}
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Purchased");
		paymentStatsTemp.put("value", String.valueOf(totalQuantity));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Sold");
		paymentStatsTemp.put("value", String.valueOf(totalSold));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	public static List<Map<String, String>> getPaymentStats(List<Bill> bills) {
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Remaining");
		paymentStatsTemp.put("value", String.valueOf(getAdjustedAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Sold");
		paymentStatsTemp.put("value", String.valueOf(getPendingAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	public static Double getPendingAmount(List<Bill> bills) {
		double pendingAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				pendingAmount += bill.getDueAmount();
			}
		}
		return pendingAmount;
	}
	
	public static Double getAdjustedAmount(List<Bill> bills) {
		double adjustedAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				adjustedAmount += bill.getPaidAmount();
			}
		}
		return adjustedAmount;
	}
	@GetMapping("/search/{searchText}/")
	public List<Item> getItems(@PathVariable("searchText") String searchText) {
		List<Item> itemList = itemService.getAllItemsLike(searchText);
		return itemList;
	}
}
