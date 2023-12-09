package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;

import com.project.shop.hardware.hassanhardware.entity.SoldItems;

public interface SoldItemsService {

	List<SoldItems> getSoldItems(long billId);
	List<SoldItems> getSoldItemsForItemRecord(long id);
	List<SoldItems> getSoldItemsForItemRecord(long id, Date purchaseDate);
	List<SoldItems> getSoldItemsForItem(long id);
	List<SoldItems> getSoldItemsForItemAfter(long id, Date purchaseDate);
}
