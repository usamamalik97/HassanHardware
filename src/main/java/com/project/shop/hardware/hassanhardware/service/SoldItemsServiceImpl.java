package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.SoldItems;

@Service
public class SoldItemsServiceImpl extends DaoConnectionService implements SoldItemsService {

	@Override
	public List<SoldItems> getSoldItems(long billId) {
		return soldItemsRepository.findByBillBillId(billId);
	}

	@Override
	public List<SoldItems> getSoldItemsForItemRecord(long id) {
		return soldItemsRepository.findByItemRecordId(id);

	}
	@Override
	public List<SoldItems> getSoldItemsForItem(long id) {
		return soldItemsRepository.findByItemRecordItemItemId(id);

	}
	@Override
	public List<SoldItems> getSoldItemsForItemRecord(long id, Date purchaseDate) {
		return soldItemsRepository.findByItemRecordIdAndPurchaseDateGreaterThan(id, purchaseDate);

	}
	@Override
	public List<SoldItems> getSoldItemsForItemAfter(long id, Date purchaseDate) {
		return soldItemsRepository.findByItemRecordItemItemIdAndPurchaseDateGreaterThan(id, purchaseDate);

	}
}
