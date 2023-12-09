package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.ShopType;

@Service
public class ShopServiceImpl extends DaoConnectionService implements ShopService {

	
	@Override
	public ShopType saveShopType(ShopType shopType) {
		Date currentDate = new Date(new java.util.Date().getTime());
		shopType.setCreatedOn(currentDate);
		shopType.setModifiedOn(currentDate);
		
		return shopTypeRepository.save(shopType);
	}

	@Override
	public Shop saveShop(Shop shop) {
		return shopRepository.save(shop);
	}

	@Override
	public List<Shop> getShops() {
		return shopRepository.findAll();
	}

	@Override
	public Shop getShop(long shopId) {
		Optional<Shop> shopOptional = shopRepository.findById(shopId);
		if(shopOptional.isPresent()){
			return shopOptional.get();
		}
		return null;
	}

	@Override
	public List<Customer> getShopsCustomer(long shopId) {
		return customerRepository.findByShopShopId(shopId);
	}

	@Override
	public List<Shop> getShops(String type) {
		return shopRepository.findByShopTypeType(type);
	}

	@Override
	public ShopType getShopType(String shopType) {
		Optional<ShopType> shopTypeOptional = shopTypeRepository.findById(shopType);
		if(shopTypeOptional.isPresent()){
			return shopTypeOptional.get();
		}
		return null;
	}

	@Override
	public List<ShopType> getShopTypes() {
		return shopTypeRepository.findAll();
	}

	@Override
	public List<Bill> getShopBills(long shopId) {
		return billRepository.findByCustomerShopShopId(shopId);
	}

	@Override
	public List<Shop> getAllShopsLike(String searchText) {
		return shopRepository.findByShopNameContainingIgnoreCase(searchText);
	}

}
