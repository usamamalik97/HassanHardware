package com.project.shop.hardware.hassanhardware.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.shop.hardware.hassanhardware.beans.BillBean;
import com.project.shop.hardware.hassanhardware.beans.ChequeBean;
import com.project.shop.hardware.hassanhardware.beans.CustomerBean;
import com.project.shop.hardware.hassanhardware.beans.ShopBean;
import com.project.shop.hardware.hassanhardware.beans.SoldItemsBean;
import com.project.shop.hardware.hassanhardware.beans.TransactionInfoBean;
import com.project.shop.hardware.hassanhardware.beans.UserBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.BillPayment;
import com.project.shop.hardware.hassanhardware.entity.BillStatus;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.ItemRecord;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.ShopType;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.service.BillService;
import com.project.shop.hardware.hassanhardware.service.ChequeService;
import com.project.shop.hardware.hassanhardware.service.CustomerService;
import com.project.shop.hardware.hassanhardware.service.ExpenseService;
import com.project.shop.hardware.hassanhardware.service.ItemService;
import com.project.shop.hardware.hassanhardware.service.ShopService;
import com.project.shop.hardware.hassanhardware.service.SoldItemsService;
import com.project.shop.hardware.hassanhardware.service.TransactionInfoService;
import com.project.shop.hardware.hassanhardware.service.UserService;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@CrossOrigin
@RestController
public class BillController {

	@Autowired
	private RequestMapper mapper;
	
	@Autowired
	private BillService billService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private SoldItemsService soldItemsService;

	@Autowired
	private ChequeService chequeService;
	
	@Autowired
	private TransactionInfoService transactionInfoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping("/hassan-hardware/bill/add")
	public Bill saveBill(@RequestParam("Customer") String customer, @RequestParam("Shop") String shop, @RequestParam("Items") String items, @RequestParam("Bill") String bill, @RequestParam("Cheque") String cheque, @RequestParam("User") String user) {
		List<SoldItemsBean> soldItemsBean = mapper.mapValuesToList(items, new TypeReference<List<SoldItemsBean>>() {});
		UserBean userBean = mapper.mapValues(user, UserBean.class);
		CustomerBean customerBean = mapper.mapValues(customer, CustomerBean.class);
		ShopBean shopBean = mapper.mapValues(shop, ShopBean.class);
		BillBean billBean = mapper.mapValues(bill, BillBean.class);
		ChequeBean chequeBean = mapper.mapValues(cheque, ChequeBean.class);
		chequeBean.setChequeCashed(Constants.OPTION_NO);
		Cheque chequeEntity = chequeBean.getEntityObject();
		Customer customerEntity;
		Shop shopEntity;
		ShopType shopType;
		
		User userEntity = userService.getUser(userBean.getUsername());
		boolean isAdmin = Constants.ROLE_ADMIN.equals(userEntity.getRole());
		if(customerBean.getCustomerId() > 0) {
			customerEntity = customerService.getCustomer(customerBean.getCustomerId());
		} else {
			customerEntity = customerBean.getEntityObject();
			if(shopBean.getShopId() > 0  || !CommonUtils.isNullOrEmptyString(shopBean.getShopName())) {
				if(shopBean.getShopId() > 0) {
					shopEntity = shopService.getShop(shopBean.getShopId());
					customerEntity.setShop(shopEntity);
				} else {
					shopEntity = shopBean.getEntityObject();
					shopType = shopService.getShopType(shopBean.getShopType());
					if(null != shopType) {
						shopEntity.setShopType(shopType);
					}
					customerEntity.setShop(shopService.saveShop(shopEntity));
				}
			}
			customerEntity = customerService.saveCustomer(customerEntity);				
		}

		Bill billEntity = new Bill();
		billEntity.setTotalAmount(billBean.getTotalAmount());
		billEntity.setDueAmount(billBean.getTotalAmount());

		billEntity.setBillStatus(!isAdmin || (billBean.getPaidAmount() == 0 && billBean.getTotalAmount() > 0)
				? billService.getBillStatus(Constants.BILL_STATUS_UNPAID)
				: billBean.getPaidAmount() >= billBean.getTotalAmount()
						? billService.getBillStatus(Constants.BILL_STATUS_FULL_PAID)
						: billService.getBillStatus(Constants.BILL_STATUS_PARTIAL_PAID));
		
		Date currentDate = new Date(new java.util.Date().getTime());
		billEntity.setBillDate(currentDate);
		billEntity.setDueDate(currentDate);
		billEntity.setCustomer(customerEntity);
		billEntity = billService.saveBill(billEntity);
		List<SoldItems> soldItems = new ArrayList<>();
		for(SoldItemsBean soldItemBean: soldItemsBean) {
			Item item = itemService.getItem(soldItemBean.getItemRecordId());
			soldItems.addAll(getSoldItems(soldItemBean, item, billEntity));
			itemService.saveItem(item);
		}
		TransactionInfo transaction = new TransactionInfo();
		transaction.setCustomer(customerEntity);
		transaction.setPayeeName(customerEntity.getCustomerName());
		transaction.setPaymentAmount(billBean.getPaidAmount());
		transaction.setPaymentDate(currentDate);
		transaction.setUser(userEntity);
		itemService.saveSoldItems(soldItems);
		transaction.setApproved(isAdmin ? Constants.OPTION_YES : Constants.OPTION_NO);
		transaction.setBillStatus(billService.getBillStatus(Constants.BILL_STATUS_UNPAID));
		transaction = transactionInfoService.saveTransactionEntity(transaction);
		if(isAdmin) {
			billService.settleTransaction(billEntity, transaction);
			if(transaction.getAdjustedAmount() < transaction.getPaymentAmount()) {
				billService.saveTransaction(transaction);
			}
			if(transaction.getAdjustedAmount() < transaction.getPaymentAmount()) {
				billService.saveShopTransaction(transaction);
			}			
		}
		if(chequeEntity.getAmount() > 0 && !CommonUtils.isNullOrEmptyString(chequeEntity.getBank()) && chequeEntity.getDueDate() != null) {
			chequeEntity.setCustomer(customerEntity);
			chequeEntity.setApproved(isAdmin ? Constants.OPTION_YES : Constants.OPTION_NO);
			chequeService.saveCheque(chequeEntity);
		}
		if(billEntity.getDueAmount() > 0) {
			settleBillWithTransactions(customerEntity);			
		}
		return billService.getBill(billEntity.getBillId());
	}
	private void settleBillWithTransactions(Customer customerEntity) {
		List<TransactionInfo> customerTransactions = transactionInfoService.getCustomerUnsettledTransaction(customerEntity.getCustomerId());
		settleTransactions(customerTransactions);
		List<TransactionInfo> shopTransactions = transactionInfoService.getShopUnsettledTransaction(customerEntity.getCustomerId());
		settleTransactions(shopTransactions);
	}
	private void settleTransactions(List<TransactionInfo> transactions) {
		if(transactions != null) {
			for(TransactionInfo transactionInfo: transactions) {
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveTransaction(transactionInfo);
				}
			}
			for(TransactionInfo transactionInfo: transactions) {
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveShopTransaction(transactionInfo);
				}
			}
		}
	}
	@PostMapping("/hassan-hardware/bills/addCustomerPayment")
	public ResponseEntity<Map<String, Object>> saveCustomerPayment(@RequestParam("Customer") String customer, @RequestParam("Transaction") String transaction, @RequestParam("Cheque") String cheque, @RequestParam("User") String user) {
		TransactionInfoBean transactionBean;
		TransactionInfo transactionInfo;
		CustomerBean customerBean;
		Customer customerObject;
		Date currentDate = new Date(new java.util.Date().getTime());

		UserBean userBean = mapper.mapValues(user, UserBean.class);
		User userEntity = userService.getUser(userBean.getUsername());
		boolean isAdmin = Constants.ROLE_ADMIN.equals(userEntity.getRole());
		customerBean = mapper.mapValues(customer, CustomerBean.class);
		transactionBean = mapper.mapValues(transaction, TransactionInfoBean.class);
		customerObject = customerService.getCustomer(customerBean.getCustomerId());
		transactionInfo = transactionBean.getEntityObject();
		if(transactionInfo.getPaymentAmount() > 0) {
			transactionInfo.setCustomer(customerObject);
			transactionInfo.setPayeeName(customerObject.getCustomerName());
			transactionInfo.setPaymentDate(currentDate);
			transactionInfo.setUser(userEntity);
			transactionInfo.setBillStatus(billService.getBillStatus(Constants.BILL_STATUS_UNPAID));
			if(isAdmin) {
				transactionInfo.setApproved(Constants.OPTION_YES);
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveTransaction(transactionInfo);
				}
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveShopTransaction(transactionInfo);
				}
			} else {
				transactionInfo.setApproved(Constants.OPTION_NO);	
				transactionInfo = transactionInfoService.saveTransactionEntity(transactionInfo);
			}
		}
		ChequeBean chequeBean = mapper.mapValues(cheque, ChequeBean.class);
		chequeBean.setChequeCashed(Constants.OPTION_NO);
		Cheque chequeEntity = chequeBean.getEntityObject();
		if(chequeEntity.getAmount() > 0 && !CommonUtils.isNullOrEmptyString(chequeEntity.getBank()) && chequeEntity.getDueDate() != null) {
			chequeEntity.setCustomer(customerObject);
			chequeEntity.setUser(userEntity);
			chequeEntity.setApproved(isAdmin ? Constants.OPTION_YES : Constants.OPTION_NO);
			chequeService.saveCheque(chequeEntity);
		}
		return customerService.getAllCustomers(transactionInfoService, chequeService);
	}
	@PostMapping("/hassan-hardware/bills/addPayment")
	public Customer saveCustomerPayment(@RequestParam("Customer") String customer, @RequestParam("Transaction") String transaction) {
		TransactionInfoBean transactionBean;
		TransactionInfo transactionInfo;
		CustomerBean customerBean;
		Customer customerObject;
		Date currentDate = new Date(new java.util.Date().getTime());

		customerBean = mapper.mapValues(customer, CustomerBean.class);
		transactionBean = mapper.mapValues(transaction, TransactionInfoBean.class);
		transactionInfo = transactionBean.getEntityObject();
		customerObject = customerService.getCustomer(customerBean.getCustomerId());

		transactionInfo.setCustomer(customerObject);
		transactionInfo.setPayeeName(customerObject.getCustomerName());
		transactionInfo.setPaymentDate(currentDate);
		
		if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
			billService.saveTransaction(transactionInfo);
		}
		if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
			billService.saveShopTransaction(transactionInfo);
		}
		return null;
    }
	@PostMapping("/hassan-hardware/payment/approved")
	public ResponseEntity<Map<String, Object>> saveCustomerPayment(@RequestParam("Transaction") String transaction) {
		saveTransaction(transaction);
		Date currentDate = new Date(new java.util.Date().getTime());
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPaymentsForDate(currentDate);
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("transactionInfoList", transactionInfoList);
		responseMap.put("chequesCashedAmount", CommonUtils.getPaymentAmount(transactionInfoList, true));
		responseMap.put("cashReceivedAmount", CommonUtils.getPaymentAmount(transactionInfoList, false));
		responseMap.put("expenses", CommonUtils.getExpensesAmount(currentDate, expenseService));
		return ResponseEntity.ok(responseMap);
    }
	private void saveTransaction(String transaction) {
		TransactionInfoBean transactionBean;
		TransactionInfo transactionInfo;

		transactionBean = mapper.mapValues(transaction, TransactionInfoBean.class);
		transactionInfo = transactionInfoService.getPaymentTransaction(transactionBean.getTransId());
		transactionInfo.setApproved(Constants.OPTION_YES);
		transactionInfoService.saveTransactionEntity(transactionInfo);
		if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
			billService.saveTransaction(transactionInfo);
		}
		if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
			billService.saveShopTransaction(transactionInfo);
		}
	}
	@PostMapping("/hassan-hardware/transaction/approved")
	public ResponseEntity<Map<String, Object>> saveCustomerTransaction(@RequestParam("Transaction") String transaction) {
		saveTransaction(transaction);
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPayments();

		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("transactionInfoList", transactionInfoList);
		return ResponseEntity.ok(responseMap);
    }
	private List<SoldItems> getSoldItems(SoldItemsBean soldItemBean, Item item, Bill billEntity) {
		List<ItemRecord> itemRecords = itemService.getItemRecords(item.getItemId());
		List<SoldItems> soldItems = new ArrayList<>();
		SoldItems soldItem;
		long quantity = soldItemBean.getQuantitySold();
		long availableQuantity = 0;
		for(ItemRecord itemRecord: itemRecords) {
			if(quantity <= 0) break;
			if(itemRecord.getQuantityRemaining() <= 0) continue;
			
			soldItem = soldItemBean.getEntityObject();
			availableQuantity = itemRecord.getQuantityRemaining();
			if(quantity < availableQuantity) {
				availableQuantity = quantity;
			}
			itemRecord.setQuantitySold(itemRecord.getQuantitySold() + availableQuantity);
			itemRecord.setQuantityRemaining(itemRecord.getQuantityRemaining() - availableQuantity);
			item.setQuantity(item.getQuantity() - availableQuantity);
			
			soldItem.setQuantitySold(availableQuantity);
			soldItem.setBill(billEntity);
			soldItem.setItemRecord(itemRecord);
			quantity -= availableQuantity;
			
			soldItems.add(soldItem);
		}
		return soldItems;
	}
	
	@PostMapping("/hassan-hardware/bill-statuses/add/")
	public BillStatus saveBillStatus(@ModelAttribute BillStatus billStatus) {
		return billService.saveBillStatus(billStatus);
	}

	@PostMapping("/hassan-hardware/add-transaction/")
	public List<Bill> saveBillPayment(@ModelAttribute TransactionInfo transactionInfo) {
		return billService.saveTransaction(transactionInfo);
	}
	@GetMapping("/hassan-hardware/bills/all/")
	public List<Bill> getBills() {
		List<Bill> bills = billService.getBills();
		bills.forEach(bill -> bill.getCustomer().setShopName(bill.getCustomer().getShop() == null ? "Local Customer" : bill.getCustomer().getShop().getShopName()));
		return bills;
	}
	@GetMapping("/hassan-hardware/bills/{id}/")
	public ResponseEntity<Map<String,Object>> getBill(@PathVariable("id") long billId) {
		List<SoldItems> soldItems = soldItemsService.getSoldItems(billId);
		Bill bill = billService.getBill(billId);
		Map<String, Object> responseMap = new HashMap<>();
		bill.getCustomer().setHoldAmount(getCustomerHoldAmount(bill.getCustomer().getCustomerId()));
        responseMap.put("customer", bill.getCustomer());
        responseMap.put("soldItems", soldItems);
        responseMap.put("bill", bill);
        responseMap.put("items", getItemMapForSoldItems(soldItems));
        
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
	private Object getItemMapForSoldItems(List<SoldItems> soldItems) {
		Map<Long, Item> itemMap = new ConcurrentHashMap<>();
		for(SoldItems soldItem: soldItems) {
			itemMap.put(soldItem.getId(), soldItem.getItemRecord().getItem());
		}
		return itemMap;
	}
	@GetMapping("/hassan-hardware/bill-statuses/all/")
	public List<BillStatus> getBillStatuses() {
		return billService.getBillStatuses();
	}
	@GetMapping("/hassan-hardware/bills/pending/")
	public List<Bill> getPendingBillsExcept() {
		return billService.getBillsExcept(Constants.BILL_STATUS_FULL_PAID);
	}
	
	@GetMapping("/hassan-hardware/bills/paid/")
	public List<Bill> getPaidBills() {
		return billService.getBills(Constants.BILL_STATUS_FULL_PAID);
	}
	
	@GetMapping("/hassan-hardware/bills/unpaid/")
	public List<Bill> getUnpaidBills() {
		return billService.getBills(Constants.BILL_STATUS_UNPAID);
	}
	
	@GetMapping("/hassan-hardware/bills/{id}/payments/")
	public List<BillPayment> getBillPayments(@PathVariable("id") long billId) {
		return billService.getBillPayments(billId);
	}
	@GetMapping("/hassan-hardware/bills/{id}/payments/{paymentId}/")
	public BillPayment getBillPayments(@PathVariable("id") long billId, @PathVariable("paymentId") long paymentId) {
		return billService.getBillPayment(billId, paymentId);
	}
	
}
