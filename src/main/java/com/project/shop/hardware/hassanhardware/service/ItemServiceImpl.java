package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;

@Service
public class ItemServiceImpl extends DaoConnectionService implements ItemService {
	private static final Logger LGR = LogManager.getLogger(ItemServiceImpl.class);

	@Override
	public Item saveItem(Item item, long quantity) {
		LGR.info("Going to persist customer: " + item);
		List<Item> items = itemRepository.findByItemSizeAndItemWeightAndItemMaterialAndBrandBrandName(item.getItemSize(), item.getItemWeight(), item.getItemMaterial(), item.getBrand().getBrandName());
		if(items == null || items.size() == 0) {
			item.setQuantity(quantity);
			return itemRepository.save(item);
		} else {
			items.get(0).setQuantity(items.get(0).getQuantity() + quantity);
			return itemRepository.save(items.get(0));
		}
	}

	@Override
	public Item saveItem(Item item) {
		LGR.info("Going to persist item: " + item);
		return itemRepository.save(item);
	}
	@Override
	public List<SoldItems> saveSoldItems(List<SoldItems> soldItems) {
		LGR.info("Going to persist soldItems: " + soldItems);
		return soldItemsRepository.saveAll(soldItems);
	}
	@Override
	public ItemRecord saveItemRecord(ItemRecord itemRecord) {

		Date currentDate = new Date(new java.util.Date().getTime());
		itemRecord.setPurchaseDate(currentDate);
		itemRecord.setCreatedOn(currentDate);
		itemRecord.setModifiedOn(currentDate);
		itemRecord.setQuantityRemaining(itemRecord.getQuantity());
		itemRecord.setTotalCost(itemRecord.getQuantity() * itemRecord.getCostPrice());
		itemRecord.setTotalWeight(itemRecord.getQuantity() * itemRecord.getItem().getItemWeight());

		LGR.info("Going to persist itemRecord: " + itemRecord);
		return itemRecordRepository.save(itemRecord);
	}

	@Override
	public List<Item> getItems() {
		return itemRepository.findAll();
	}
	@Override
	public List<ItemRecord> getItemRecords(long itemId) {
		List<ItemRecord> itemRecords = itemRecordRepository.findByItemItemId(itemId);
		return itemRecords;
	}

	@Override
	public List<ItemRecord> getItemRecordsAfterDate(long itemId, Date purchaseDate) {
		List<ItemRecord> itemRecords = itemRecordRepository.findByItemItemIdAndPurchaseDateGreaterThan(itemId, purchaseDate);
		return itemRecords;
	}
	@Override
	public Item getItem(long itemId) {
		Optional<Item> optional = itemRepository.findById(itemId);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Item> getHomeDataItems() {
		return itemRepository.findTop10ByOrderByItemId();
	}

	@Override
	public List<Item> getAllItemsLike(String searchText) {
		return itemRepository.findByItemSizeContainingIgnoreCase(searchText);
	}

	@Override
	public List<ItemRecord> getItemRecordsWithDate(Date purchaseDate) {
		return itemRecordRepository.findByPurchaseDate(purchaseDate);
	}
}
