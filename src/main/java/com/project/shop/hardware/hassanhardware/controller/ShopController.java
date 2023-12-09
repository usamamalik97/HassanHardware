package com.project.shop.hardware.hassanhardware.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.ShopType;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.service.BillService;
import com.project.shop.hardware.hassanhardware.service.ChequeService;
import com.project.shop.hardware.hassanhardware.service.CustomerService;
import com.project.shop.hardware.hassanhardware.service.ShopService;
import com.project.shop.hardware.hassanhardware.service.TransactionInfoService;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;

@CrossOrigin
@RestController
@RequestMapping("/hassan-hardware/shops")
public class ShopController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private BillService billService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ChequeService chequeService;

	@Autowired
	private TransactionInfoService transactionInfoService;
	
	@PostMapping("/shop_types/add")
	public ShopType saveShopType(@ModelAttribute ShopType shopType) {
		return shopService.saveShopType(shopType);
	}

	@PostMapping("/add")
	public Shop saveShop(@ModelAttribute Shop shop) {
		return shopService.saveShop(shop);
	}
	@GetMapping("/shop_types/")
	public List<ShopType> getShopTypes() {
		return shopService.getShopTypes();
	}
	@GetMapping("/")
	public ResponseEntity<Map<String,Object>> getShops() {
		Map<String, Object> billsMap = new HashMap<>();
		List<Shop> shops = shopService.getShops();
		for(Shop shop: shops) {
	        List<Bill> shopBills = shopService.getShopBills(shop.getShopId());
	        if(shopBills == null) {
	        	shopBills = new ArrayList<>();
	        }
			billsMap.put(String.valueOf(shop.getShopId()), shopBills);
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("shops", shops);
        responseMap.put("bills", billsMap);

        return ResponseEntity.ok(responseMap);
	}
	/*private List<Map<String, String>> getPaymentStats(Map<String, Object> billsMap) {
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Payments");
		paymentStatsTemp.put("value", String.valueOf(getAdjustedAmount(billsMap)));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Pending");
		paymentStatsTemp.put("value", String.valueOf(getPendingAmount(billsMap)));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}*/
	
	/*private Double getPendingAmount(Map<String, Object> billsMap) {
		double pendingAmount = 0.0;
		if(billsMap != null && billsMap.size() > 0) {
			for(Map.Entry<String, Object> entry: billsMap.entrySet()) {
				if(entry.getValue() != null) {
					for(Bill bill: (List<Bill>) entry.getValue()) {
						pendingAmount += bill.getDueAmount();
					}
					
				}
			}
		}
		return pendingAmount;
	}
	
	private Double getAdjustedAmount(Map<String, Object> billsMap) {
		double adjustedAmount = 0.0;
		if(billsMap != null && billsMap.size() > 0) {
			for(Map.Entry<String, Object> entry: billsMap.entrySet()) {
				if(entry.getValue() != null) {
					for(Bill bill: (List<Bill>) entry.getValue()) {
						adjustedAmount += bill.getPaidAmount();
					}
					
				}
			}
		}
		return adjustedAmount;
	}*/
	
	/*@GetMapping("/{type}/")
	public List<Shop> getShop(@PathVariable("type") String type) {
		return shopService.getShops(type);
	}*/

	/*@GetMapping("/{id}/customers/")
	public List<Customer> getShopsCustomer(@PathVariable("id") long shopId) {
		return shopService.getShopsCustomer(shopId);
	}*/
	@GetMapping("/{shopId}/")
    public ResponseEntity<Map<String, Object>> getShopDetails(@PathVariable("shopId") long shopId) {
        List<Bill> bills;
		List<Customer> data1 = shopService.getShopsCustomer(shopId);
        Object data2 = shopService.getShop(shopId);
        bills = billService.getShopBills(shopId);
        Object data3 = bills == null ? new ArrayList<>() : bills;

		List<Map<String, String>> paymentStats = CommonUtils.getPaymentStats(bills);
		
		Map<String, Object> billsMap = new HashMap<>();
		for(Customer customer: data1) {
			customer.setShopName(customer.getShop().getShopName());
	        List<Bill> customerBills = customerService.getCustomerBills(customer.getCustomerId());
	        if(customerBills == null) {
	        	customerBills = new ArrayList<>();
	        }
	        customer.setHoldAmount(getCustomerHoldAmount(customer.getCustomerId()));
			billsMap.put(String.valueOf(customer.getCustomerId()), customerBills);
		}
		
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("customers", data1);
        responseMap.put("shop", data2);
        responseMap.put("bills", data3);
        responseMap.put("paymentStats", paymentStats);
        responseMap.put("customerBills", billsMap);

        return ResponseEntity.ok(responseMap);
    }
	private double getCustomerHoldAmount(long customerId) {
		double totalHoldAmount = 0;
		List<Cheque> cheques = chequeService.getCustomerPendingCheques(customerId);
		if(cheques != null) {
			for(Cheque cheque: cheques) {
				totalHoldAmount += cheque.getAmount();
			}
		}
		List<TransactionInfo> transactionInfoList = transactionInfoService.getCustomerPendingTransactions(customerId);
		if(transactionInfoList != null) {
			for(TransactionInfo transactionInfo: transactionInfoList) {
				totalHoldAmount += transactionInfo.getPaymentAmount();
			}
		}
		return totalHoldAmount;
	}
	@GetMapping("/search/{searchText}/")
	public ResponseEntity<Map<String,Object>> getShops(@PathVariable("searchText") String searchText) {
		Map<String, Object> billsMap = new HashMap<>();
		List<Shop> shops = shopService.getAllShopsLike(searchText);
		for(Shop shop: shops) {
	        List<Bill> shopBills = shopService.getShopBills(shop.getShopId());
	        if(shopBills == null) {
	        	shopBills = new ArrayList<>();
	        }
			billsMap.put(String.valueOf(shop.getShopId()), shopBills);
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("shops", shops);
        responseMap.put("bills", billsMap);

        return ResponseEntity.ok(responseMap);
	}
}
