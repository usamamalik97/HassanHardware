package com.project.shop.hardware.hassanhardware.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;


@Service
public class CustomerServiceImpl extends DaoConnectionService implements CustomerService {

	private static final Logger LGR = LogManager.getLogger(CustomerService.class);
	
	@Override
	public Customer saveCustomer(Customer customer) {
		LGR.info("Going to persist customer: " + customer);
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getCustomers() {
		LGR.info("Going to fetch all customers");
		return customerRepository.findAll();
	}
	@Override
	public Customer getCustomer(long customerId) {
		LGR.info("Going to fetch customer against customerId: " + customerId);
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(customerOptional.isPresent()){
			return customerOptional.get();
		}
		return null;
	}

	@Override
	public List<Bill> getCustomerBills(long customerId) {
		LGR.info("Going to fetch bill for customerId: " + customerId);
		return billRepository.findByCustomerCustomerId(customerId);
	}

	@Override
	public Bill getCustomerBill(long customerId, long billId) {
		LGR.info("Going to fetch bill for customerId: " + customerId + " billId: " + billId);
		Optional<Bill> billOptional = billRepository.findById(billId);
		if(billOptional.isPresent()){
			return billOptional.get();
		}
		return null;
	}

	@Override
	public List<Bill> getCustomerBills(long customerId, String billStatus) {
		LGR.info("Going to fetch bill for customerId: " + customerId + " billStatus: " + billStatus);
		return billRepository.findByCustomerCustomerIdAndBillStatusStatus(customerId, billStatus);
	}

	@Override
	public List<Bill> getCustomerBillsExcept(long customerId, String billStatus) {
		return billRepository.findByCustomerCustomerIdAndBillStatusStatusNot(customerId, billStatus);
	}

	@Override
	public List<Customer> getHomeDataCustomers() {
		return customerRepository.findTop8ByOrderByCustomerId();
	}

	@Override
	public List<Customer> getAllCustomersLike(String searchText) {
		return customerRepository.findByCustomerNameOrShopNameContainingIgnoreCase(searchText);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllCustomers(TransactionInfoService transactionInfoService, ChequeService chequeService) {
		Map<String, Object> billsMap = new HashMap<>();
		Map<String, Double> customerRemainingBalance = new HashMap<>();
		List<Customer> customers = getCustomers();
		for(Customer customer: customers) {
			customer.setShopName(customer.getShop() == null ? "Local Customer" : customer.getShop().getShopName());
	        List<Bill> customerBills = getCustomerBills(customer.getCustomerId());
	        if(customerBills == null) {
	        	customerBills = new ArrayList<>();
	        }
	        customer.setHoldAmount(getCustomerHoldAmount(customer.getCustomerId(), transactionInfoService, chequeService));
			billsMap.put(String.valueOf(customer.getCustomerId()), customerBills);
			customerRemainingBalance.put(String.valueOf(customer.getCustomerId()), CommonUtils.getPendingTransactionsBalance(transactionInfoService.getCustomerExtraTransactions(customer.getCustomerId())));
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("customers", customers);
        responseMap.put("bills", billsMap);
        responseMap.put("customerRemainingBalance", customerRemainingBalance);

        return ResponseEntity.ok(responseMap);
	}
	@Override
	public double getCustomerHoldAmount(long customerId, TransactionInfoService transactionInfoService, ChequeService chequeService) {
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
}
