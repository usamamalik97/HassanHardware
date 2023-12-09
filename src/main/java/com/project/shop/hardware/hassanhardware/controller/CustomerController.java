package com.project.shop.hardware.hassanhardware.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.beans.CustomerBean;
import com.project.shop.hardware.hassanhardware.beans.ShopBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.ShopType;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.service.ChequeService;
import com.project.shop.hardware.hassanhardware.service.CustomerService;
import com.project.shop.hardware.hassanhardware.service.ShopService;
import com.project.shop.hardware.hassanhardware.service.TransactionInfoService;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@CrossOrigin
@RestController
@RequestMapping("/hassan-hardware/customers")
public class CustomerController {
	@Autowired
	private RequestMapper mapper;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ChequeService chequeService;

	@Autowired
	private TransactionInfoService transactionInfoService;
	
	@PostMapping("/add")
	public Customer saveCustomer(@RequestParam("Customer") String customer, @RequestParam("Shop") String shop) {
		Shop shopObject;
		ShopBean shopBean;
		ShopType shopType;
		CustomerBean customerBean;
		Customer customerObject;
		
		customerBean = mapper.mapValues(customer, CustomerBean.class);
		customerObject = customerBean.getEntityObject();
		shopBean = mapper.mapValues(shop, ShopBean.class);
		if(shopBean.getShopId() > 0) {
			shopObject = shopService.getShop(shopBean.getShopId());
			if(null != shopObject) {
				customerObject.setShop(shopObject);
			}
		} else {
			shopObject = shopBean.getEntityObject();
			shopType = shopService.getShopType(shopBean.getShopType());
			if(null != shopType) {
				shopObject.setShopType(shopType);
			}
			customerObject.setShop(shopService.saveShop(shopObject));
		}
		return customerService.saveCustomer(customerObject);
    }
	
	@GetMapping("/")
	public List<Customer> getCustomers() {
		List<Customer> customers = customerService.getCustomers();
		for(Customer customer: customers) {
			customer.setCustomerName(customer.getCustomerName() + " Shop: " + customer.getShop().getShopName());
	        customer.setHoldAmount(customerService.getCustomerHoldAmount(customer.getCustomerId(), transactionInfoService, chequeService));
		}
		return customers;
	}
	
	@GetMapping("/all/")
	public ResponseEntity<Map<String,Object>> getAllCustomers() {
		return customerService.getAllCustomers(transactionInfoService, chequeService);
	}

	@GetMapping("/{id}/bills/")
	public List<Bill> getCustomerBills(@PathVariable("id") long customerId) {
		return customerService.getCustomerBills(customerId);
	}
	
	@GetMapping("/{id}/bills/{billId}/")
	public Bill getCustomerBillDetail(@PathVariable("id") long customerId, @PathVariable("billId") long billId) {
		return customerService.getCustomerBill(customerId, billId);
	}
	
	@GetMapping("/{id}/bills/unpaid/")
	public List<Bill> getCustomerBillsPending(@PathVariable("id") long customerId) {
		return customerService.getCustomerBillsExcept(customerId, Constants.BILL_STATUS_FULL_PAID);
	}
	
	@GetMapping("/{id}/bills/pending/")
	public List<Bill> getCustomerBillsUnpaid(@PathVariable("id") long customerId) {
		return customerService.getCustomerBills(customerId, Constants.BILL_STATUS_UNPAID);
	}
	
	@GetMapping("/{id}/bills/paid/")
	public List<Bill> getCustomerBillsPaid(@PathVariable("id") long customerId) {
		return customerService.getCustomerBills(customerId, Constants.BILL_STATUS_FULL_PAID);
	}
	@GetMapping("/{customerId}/")
    public ResponseEntity<Map<String, Object>> getCustomerDetails(@PathVariable("customerId") long customerId) {
        Customer data1 = customerService.getCustomer(customerId);
        Shop data2 = data1.getShop() != null ? data1.getShop() : new Shop();
        Object data3 = customerService.getCustomerBills(data1.getCustomerId());
		List<Map<String, String>> paymentStats = CommonUtils.getPaymentStats((List<Bill>) data3);
        data1.setShopName(CommonUtils.isNullOrEmptyString(data2.getShopName()) ? "Local Customer" : data2.getShopName());
		data1.setHoldAmount(customerService.getCustomerHoldAmount(data1.getCustomerId(), transactionInfoService, chequeService));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("customer", data1);
        responseMap.put("shop", data2);
        responseMap.put("bills", data3);
        responseMap.put("paymentStats", paymentStats);
        responseMap.put("remainingBalance", CommonUtils.getPendingTransactionsBalance(transactionInfoService.getCustomerExtraTransactions(data1.getCustomerId())));

        return ResponseEntity.ok(responseMap);
    }
	@GetMapping("/search/{searchText}/")
    public ResponseEntity<Map<String, Object>> getCustomerDetails(@PathVariable("searchText") String searchText) {
		Map<String, Object> billsMap = new HashMap<>();
		Map<String, Double> customerRemainingBalance = new HashMap<>();
		List<Customer> customers = customerService.getAllCustomersLike(searchText);
		for(Customer customer: customers) {
			customer.setShopName(customer.getShop() != null ? customer.getShop().getShopName() : "Local Customer");
	        List<Bill> customerBills = customerService.getCustomerBills(customer.getCustomerId());
	        if(customerBills == null) {
	        	customerBills = new ArrayList<>();
	        }
	        customer.setHoldAmount(customerService.getCustomerHoldAmount(customer.getCustomerId(), transactionInfoService, chequeService));
			billsMap.put(String.valueOf(customer.getCustomerId()), customerBills);
			customerRemainingBalance.put(String.valueOf(customer.getCustomerId()), CommonUtils.getPendingTransactionsBalance(transactionInfoService.getCustomerExtraTransactions(customer.getCustomerId())));
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("customers", customers);
        responseMap.put("bills", billsMap);
        responseMap.put("customerRemainingBalance", customerRemainingBalance);

        return ResponseEntity.ok(responseMap);
    }

}
