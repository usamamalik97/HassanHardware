package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.ItemRecord;

@Repository
public interface ItemRecordRepository  extends JpaRepository<ItemRecord, Long>{
	List<ItemRecord> findByItemItemId(long itemId);
	List<ItemRecord> findByItemItemIdAndPurchaseDateGreaterThan(long itemId, Date purchaseDate);
	List<ItemRecord> findByPurchaseDate(Date purchaseDate);
}
