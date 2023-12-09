package com.project.shop.hardware.hassanhardware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
	List<Shop> findByShopTypeType(String shopType);
	List<Shop> findByShopNameContainingIgnoreCase(String searchText);
}
