package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;

import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;

public interface ItemService {

	Item saveItem(Item item, long quantity);
	
	Item saveItem(Item item);

	ItemRecord saveItemRecord(ItemRecord itemRecord);
	
	List<Item> getItems();
	
	List<ItemRecord> getItemRecords(long itemId);

	Item getItem(long itemId);
	
	List<SoldItems> saveSoldItems(List<SoldItems> soldItems);

	List<Item> getHomeDataItems();
	List<ItemRecord> getItemRecordsAfterDate(long itemId, Date purchaseDate);

	List<Item> getAllItemsLike(String searchText);

	List<ItemRecord> getItemRecordsWithDate(Date purchaseDate);

}
