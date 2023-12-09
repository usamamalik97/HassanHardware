package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.SoldItems;

@Repository
public interface SoldItemsRepository extends JpaRepository<SoldItems, Long> {
	List<SoldItems> findByBillBillId(long billId);
	List<SoldItems> findByItemRecordId(long id);
	List<SoldItems> findByItemRecordItemItemId(long itemId);
	List<SoldItems> findByItemRecordIdAndPurchaseDateGreaterThan(long id, Date purchaseDate);
	List<SoldItems> findByItemRecordItemItemIdAndPurchaseDateGreaterThan(long id, Date purchaseDate);
}