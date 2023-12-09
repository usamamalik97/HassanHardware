package com.project.shop.hardware.hassanhardware.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

/*import com.project.shop.hardware.hassanhardware.beans.CustomerBean;
import com.project.shop.hardware.hassanhardware.beans.TransactionInfoBean;*/
import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Customer;
//import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;

public interface CustomerService {

	Customer saveCustomer(Customer customer);

	List<Customer> getCustomers();

	Customer getCustomer(long customerId);

	List<Bill> getCustomerBills(long customerId);

	Bill getCustomerBill(long customerId, long billId);

	List<Bill> getCustomerBills(long customerId, String billStatus);

	List<Bill> getCustomerBillsExcept(long customerId, String billStatus);

	List<Customer> getHomeDataCustomers();

	List<Customer> getAllCustomersLike(String searchText);

	ResponseEntity<Map<String, Object>> getAllCustomers(TransactionInfoService transactionInfoService, ChequeService chequeService);

	double getCustomerHoldAmount(long customerId, TransactionInfoService transactionInfoService, ChequeService chequeService);

}
