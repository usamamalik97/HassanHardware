package com.project.shop.hardware.hassanhardware.service;

import java.util.List;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.ShopType;

public interface ShopService {

	ShopType saveShopType(ShopType shopType);
	
	ShopType getShopType(String shopType);

	List<ShopType> getShopTypes();
	
	Shop saveShop(Shop shop);

	List<Shop> getShops();
	
	Shop getShop(long shopId);

	List<Customer> getShopsCustomer(long shopId);

	List<Shop> getShops(String type);
	
	List<Bill> getShopBills(long shopId);

	List<Shop> getAllShopsLike(String searchText);

}
