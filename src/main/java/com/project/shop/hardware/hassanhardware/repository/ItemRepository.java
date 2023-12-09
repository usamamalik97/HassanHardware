package com.project.shop.hardware.hassanhardware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemSizeAndItemWeightAndItemMaterial(String itemSize, double itemWeight, String itemMaterial);
    List<Item> findTop10ByOrderByItemId();
    List<Item> findByItemSizeContainingIgnoreCase(String searchText);
	List<Item> findByItemSizeAndItemWeightAndItemMaterialAndBrandBrandName(String itemSize, double itemWeight, String itemMaterial, String brandName);
}
