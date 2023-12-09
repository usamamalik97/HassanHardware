package com.project.shop.hardware.hassanhardware.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.beans.AuthenticationResponse;
import com.project.shop.hardware.hassanhardware.beans.UserBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.Brand;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.DailyExpense;
import com.project.shop.hardware.hassanhardware.entity.Item;
import com.project.shop.hardware.hassanhardware.entity.SoldItems;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.repository.BrandRepository;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.service.BillService;
import com.project.shop.hardware.hassanhardware.service.ChequeService;
import com.project.shop.hardware.hassanhardware.service.CustomerService;
import com.project.shop.hardware.hassanhardware.service.ExpenseService;
import com.project.shop.hardware.hassanhardware.service.ItemService;
import com.project.shop.hardware.hassanhardware.service.SoldItemsService;
import com.project.shop.hardware.hassanhardware.service.TransactionInfoService;
import com.project.shop.hardware.hassanhardware.service.UserService;
import com.project.shop.hardware.hassanhardware.utils.CommonUtils;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private RequestMapper mapper;
	
	@Autowired
	private BillService billService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	SoldItemsService soldItemsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChequeService chequeService;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private TransactionInfoService transactionInfoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private BrandRepository brandRepository;
	/*@Autowired
	private AuthenticationManager authenticationManager;
	*/
	@PostMapping("/hassan-hardware/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("User") String user) {
		UserBean userbean = mapper.mapValues(user, UserBean.class);
		
		User authenticatedUser = userService.authenticateUser(userbean.getEntityObject());
		if(authenticatedUser != null) {

	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("authenticated", true);
	        responseMap.put("user", authenticatedUser);
	        return ResponseEntity.ok(responseMap);
		}
		Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Operation failed");
        errorResponse.put("details", "Some error occurred during the operation");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	@PostMapping("/hassan-hardware/authenticate")
	public User authenticate(@RequestParam("User") String user) {
//	public ResponseEntity<AuthenticationResponse> authenticate(@RequestParam("User") String user) {
		UserBean userbean = mapper.mapValues(user, UserBean.class);
		/*Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userbean.getUsername(), userbean.getPassword()));
*/
		/*AuthenticationResponse authenticatedUser = */
		return userService.authenticate(userbean.getEntityObject(), passwordEncoder);
        //return ResponseEntity.ok(authenticatedUser);
	}
	
	@GetMapping("/hassan-hardware/check-username/{username}/")
	public boolean checkUsername(@PathVariable("username") String username) {
		return userService.getUser(username) == null ? false : true;
	}
	
	@GetMapping("/hassan-hardware/getHomeData/")
	public ResponseEntity<Map<String, Object>> getHomeData() {
		Date currentDate = new Date(new java.util.Date().getTime());
		Brand newBrand = new Brand("CHIPA", currentDate, currentDate);
		brandRepository.save(newBrand);
		List<Item> itemsTemp = itemService.getItems();
		for(Item item: itemsTemp) {
			item.setBrand(newBrand);
			itemService.saveItem(item);
		}
		
		Map<String, Object> responseMap = new HashMap<>();
		List<Customer> customers = customerService.getHomeDataCustomers();
		updateCustomers(customers);
		List<User> users = userService.getHomeDataUsers();
		List<Item> items = itemService.getHomeDataItems();
		List<Bill> bills = billService.getHomeDataBills();
		List<Cheque> cheques = chequeService.getHomeDataCheques();
		List<DailyExpense> expenses = expenseService.getHomeDataExpenses();
		updateBills(bills);
		Object billStats = getHomeDataBillStats();
		Object paymentStats = getHomeDataPaymentStats();
		Map<String, Object> customerbills = new HashMap<>();
		for(Customer customer: customers) {
			customer.setShopName(customer.getShop() == null ? "Local Customer" : customer.getShop().getShopName());
	        List<Bill> customerBills = customerService.getCustomerBills(customer.getCustomerId());
	        if(customerBills == null) {
	        	customerBills = new ArrayList<>();
	        }
	        customer.setHoldAmount(getCustomerHoldAmount(customer.getCustomerId()));
			customerbills.put(String.valueOf(customer.getCustomerId()), customerBills);
		}
		responseMap.put("bills", bills);
		responseMap.put("customers", customers);
		responseMap.put("customerbills", customerbills);
		responseMap.put("users", users);
		responseMap.put("products", items);
		responseMap.put("billStats", billStats);
		responseMap.put("paymentStats", paymentStats);
		responseMap.put("cheques", cheques);
		responseMap.put("expenses", expenses);
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
	private void updateBills(List<Bill> bills) {
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				bill.getCustomer().setShopName(bill.getCustomer().getShop() == null ? "Local Customer" : bill.getCustomer().getShop().getShopName());
			}
		}
	}
	private void updateCustomers(List<Customer> customers) {
		if(customers != null && customers.size() > 0) {
			for(Customer customer: customers) {
				customer.setShopName(customer.getShop() == null ? "Local Customer" : customer.getShop().getShopName());
			}
		}
	}
	private Object getHomeDataPaymentStats() {
		return getPaymentStatsForMonths(1);

	}
	
	private Object getHomeDataBillStats() {
		return getBillStatsForMonths(1);

	}
	
	private Double getPendingAmount(List<Bill> bills) {
		double pendingAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				pendingAmount += bill.getDueAmount();
			}
		}
		return pendingAmount;
	}
	
	private Double getAdjustedAmount(List<Bill> bills) {
		double adjustedAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				adjustedAmount += bill.getPaidAmount();
			}
		}
		return adjustedAmount;
	}
	@PostMapping("/hassan-hardware/users/add")
	public User addUser(@RequestParam("User") String user) {
		UserBean userBean = mapper.mapValues(user, UserBean.class);
		userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
		return userService.saveUser(userBean.getEntityObject());
	}
	@PostMapping("/hassan-hardware/users/remove")
	public ResponseEntity<Map<String, Object>> removeUser(@RequestParam("User") String user) {
		UserBean userBean = mapper.mapValues(user, UserBean.class);
		if(userService.deleteUser(userBean.getEntityObject())) {
	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("users", userService.getUsers());
	        return ResponseEntity.ok(responseMap);
		} else {
			Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Operation failed");
	        errorResponse.put("details", "Some error occurred during the operation");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	@PostMapping("/hassan-hardware/users/changeRole")
	public ResponseEntity<Map<String, Object>> removeAdmin(@RequestParam("User") String user) {
		UserBean userBean = mapper.mapValues(user, UserBean.class);
		
		User userEntity = userService.changeRole(userBean.getEntityObject());
		if(Constants.ROLE_ADMIN.equals(userEntity.getRole())) {
			approveAllTransactionAndCheques(userEntity);
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", userService.getUsers());
        return ResponseEntity.ok(responseMap);
	}
	private void approveAllTransactionAndCheques(User userEntity) {
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPendingUserTransactions(userEntity);
		List<Cheque> chequesList = chequeService.getPendingUserCheques(userEntity);
		if(transactionInfoList != null) {
			for(TransactionInfo transactionInfo : transactionInfoList) {
				transactionInfo.setApproved(Constants.OPTION_YES);
				transactionInfoService.saveTransactionEntity(transactionInfo);
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveTransaction(transactionInfo);
				}
				if(transactionInfo.getAdjustedAmount() < transactionInfo.getPaymentAmount()) {
					billService.saveShopTransaction(transactionInfo);
				}
			}
		}
		if(chequesList != null) {
			for(Cheque cheque : chequesList) {
				cheque.setApproved(Constants.OPTION_YES);
				chequeService.saveCheque(cheque);
			}
		}
	}
	@GetMapping("/hassan-hardware/users/")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	@GetMapping("/hassan-hardware/payments/all/")
	public ResponseEntity<Map<String, Object>> getAllTransactions() {
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPayments();
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("transactionInfoList", transactionInfoList);
		return ResponseEntity.ok(responseMap);
		
	}
	@GetMapping("/hassan-hardware/payments/")
	public ResponseEntity<Map<String, Object>> getAllPayments() {
		Date currentDate = new Date(new java.util.Date().getTime());
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPaymentsForDate(currentDate);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("transactionInfoList", transactionInfoList);
		responseMap.put("chequesCashedAmount", CommonUtils.getPaymentAmount(transactionInfoList, true));
		responseMap.put("cashReceivedAmount", CommonUtils.getPaymentAmount(transactionInfoList, false));
		responseMap.put("expenses", CommonUtils.getExpensesAmount(currentDate, expenseService));
		return ResponseEntity.ok(responseMap);
	}
	
	@GetMapping("/hassan-hardware/payments/{date}/")
	public ResponseEntity<Map<String, Object>> getAllPaymentsForDate(@PathVariable("date") Date date) {
		List<TransactionInfo> transactionInfoList = transactionInfoService.getPaymentsForDate(date);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("transactionInfoList", transactionInfoList);
		responseMap.put("chequesCashedAmount", CommonUtils.getPaymentAmount(transactionInfoList, true));
		responseMap.put("cashReceivedAmount", CommonUtils.getPaymentAmount(transactionInfoList, false));
		responseMap.put("expenses", CommonUtils.getExpensesAmount(date, expenseService));
		return ResponseEntity.ok(responseMap);
	}
	@GetMapping("/hassan-hardware/statistics/")
	public ResponseEntity<Map<String, Object>> getStatistics() {
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("billStats", getBillStatistics());
		responseMap.put("paymentStats", getPaymentsStatistics());
		responseMap.put("profitStats", getProfitStatistics());
		return ResponseEntity.ok(responseMap);
	}
	public Map<String, Object> getBillStatistics() {
		Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("lastMonthBillStats", getBillStatsForMonths(1));
        responseMap.put("sixMonthBillStats", getBillStatsForMonths(6));
        responseMap.put("lastYearBillStats", getBillStatsForMonths(12));
        responseMap.put("overAllBillStats", getBillStats());

        return responseMap;
	}
	private Object getBillStats() {
		List<Map<String, String>> billStats = new ArrayList<>();
		Map<String, String> billStatsTemp;
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Full");
		billStatsTemp.put("value", String.valueOf(billService.getBills(Constants.BILL_STATUS_FULL_PAID).size()));
		billStats.add(billStatsTemp);
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Pending");
		billStatsTemp.put("value", String.valueOf(billService.getBills(Constants.BILL_STATUS_UNPAID).size()));
		billStats.add(billStatsTemp);
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Partial");
		billStatsTemp.put("value", String.valueOf(billService.getBills(Constants.BILL_STATUS_PARTIAL_PAID).size()));
		billStats.add(billStatsTemp);
		return billStats;
	}
	private Object getBillStatsForMonths(int months) {
		Date expectedDate = new Date(new java.util.Date().getTime());
		expectedDate = CommonUtils.convertToSqlDate(CommonUtils.addInDate(expectedDate, -months, Calendar.MONTH));

		List<Map<String, String>> billStats = new ArrayList<>();
		Map<String, String> billStatsTemp;
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Full");
		billStatsTemp.put("value", String.valueOf(billService.getBillsAfter(Constants.BILL_STATUS_FULL_PAID, expectedDate).size()));
		billStats.add(billStatsTemp);
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Pending");
		billStatsTemp.put("value", String.valueOf(billService.getBillsAfter(Constants.BILL_STATUS_UNPAID, expectedDate).size()));
		billStats.add(billStatsTemp);
		billStatsTemp = new ConcurrentHashMap<>();
		billStatsTemp.put("name", "Partial");
		billStatsTemp.put("value", String.valueOf(billService.getBillsAfter(Constants.BILL_STATUS_PARTIAL_PAID, expectedDate).size()));
		billStats.add(billStatsTemp);
		return billStats;
	}
	public Map<String, Object> getPaymentsStatistics() {
		Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("lastMonthPaymentStats", getPaymentStatsForMonths(1));
        responseMap.put("sixMonthPaymentStats", getPaymentStatsForMonths(6));
        responseMap.put("lastYearPaymentStats", getPaymentStatsForMonths(12));
        responseMap.put("overAllPaymentStats", getPaymentStats());

        return responseMap;
	}
	private Object getPaymentStatsForMonths(int months) {
		Date expectedDate = new Date(new java.util.Date().getTime());
		expectedDate = CommonUtils.convertToSqlDate(CommonUtils.addInDate(expectedDate, -months, Calendar.MONTH));
		List<Bill> bills = billService.getBillsAfter(expectedDate);
		
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Pending");
		paymentStatsTemp.put("value", String.valueOf(getPendingAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Received");
		paymentStatsTemp.put("value", String.valueOf(getAdjustedAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	
	public Object getPaymentStats() {
		List<Bill> bills = billService.getBills();
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Pending");
		paymentStatsTemp.put("value", String.valueOf(getPendingAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Received");
		paymentStatsTemp.put("value", String.valueOf(getAdjustedAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	public Map<String, Object> getProfitStatistics() {
		Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("lastMonthProfitStats", getProfitStats(1));
        responseMap.put("sixMonthProfitStats", getProfitStats(6));
        responseMap.put("lastYearProfitStats", getProfitStats(12));
        responseMap.put("overAllProfitStats", getProfitStats());

        return responseMap;
	}
	private Object getProfitStats(int months) {
		double totalCost = 0.0;
		double totalSale = 0.0;
		List<SoldItems> soldItems;
		Date expectedDate = new Date(new java.util.Date().getTime());
		expectedDate = CommonUtils.convertToSqlDate(CommonUtils.addInDate(expectedDate, -months, Calendar.MONTH));
		List<Bill> bills = billService.getBillsAfter(expectedDate);
		
		if(bills != null) {
			for(Bill bill: bills) {
				soldItems = soldItemsService.getSoldItems(bill.getBillId());
				for(SoldItems soldItem: soldItems) {
					totalSale += (soldItem.getSellPrice() * soldItem.getQuantitySold());
					totalCost += (soldItem.getItemRecord().getCostPrice() * soldItem.getQuantitySold());
				}
			}
		}
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Cost");
		paymentStatsTemp.put("value", String.valueOf(totalCost));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Profit");
		paymentStatsTemp.put("value", String.valueOf(totalSale - totalCost));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	private Object getProfitStats() {
		double totalCost = 0.0;
		double totalSale = 0.0;
		List<SoldItems> soldItems;
		List<Bill> bills = billService.getBills();
		
		if(bills != null) {
			for(Bill bill: bills) {
				soldItems = soldItemsService.getSoldItems(bill.getBillId());
				for(SoldItems soldItem: soldItems) {
					totalSale += (soldItem.getSellPrice() * soldItem.getQuantitySold());
					totalCost += (soldItem.getItemRecord().getCostPrice() * soldItem.getQuantitySold());
				}
			}
		}
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Cost");
		paymentStatsTemp.put("value", String.valueOf(totalCost));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Profit");
		paymentStatsTemp.put("value", String.valueOf(totalSale - totalCost));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
}
