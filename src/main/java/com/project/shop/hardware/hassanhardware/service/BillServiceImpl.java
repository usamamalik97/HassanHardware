package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.BillPayment;
import com.project.shop.hardware.hassanhardware.entity.BillStatus;
import com.project.shop.hardware.hassanhardware.entity.Customer;
import com.project.shop.hardware.hassanhardware.entity.Shop;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@Service
public class BillServiceImpl extends DaoConnectionService implements BillService{

	@Autowired
	private CustomerService customerService;
	
	@Override
	public Bill saveBill(Bill bill) {
		return billRepository.save(bill);
	}
	@Override
	public BillStatus saveBillStatus(BillStatus billStatus) {
		Date currentDate = new Date(new java.util.Date().getTime());
		billStatus.setCreatedOn(currentDate);
		billStatus.setModifiedOn(currentDate);
		
		return billStatusRepository.save(billStatus);
	}
	@Override
	public List<Bill> getBills() {
		return billRepository.findAllByOrderByBillDateDesc();
//		return billRepository.findAll();
	}

	@Override
	public Bill getBill(long billId) {
		Optional<Bill> billOptional = billRepository.findById(billId);
		if(billOptional.isPresent()){
			return billOptional.get();
		}
		return null;
	}

	@Override
	public List<BillPayment> getBillPayments(long billId) {
		return billPaymentRepository.findByBillBillId(billId);
	}

	@Override
	public BillPayment getBillPayment(long billId, long paymentId) {
		Optional<BillPayment> billPayment = billPaymentRepository.findById(paymentId);
		if(billPayment.isPresent()){
			return billPayment.get();
		}
		return null;
	}
	
	@Override
	public List<Bill> getBills(String billStatus) {
		return billRepository.findByBillStatusStatus(billStatus);
	}
	
	@Override
	public List<Bill> getBillsExcept(String billStatus) {
		Optional<BillStatus> billStatusOptional = billStatusRepository.findById(billStatus);
		if(billStatusOptional.isPresent()){
			return billRepository.findByBillStatusNot(billStatusOptional.get());
		}
		return null;
	}
	
	@Override
	public List<Bill> saveTransaction(TransactionInfo transactionInfo) {
		Customer customer = transactionInfo.getCustomer();
		List<Bill> customerBills = billRepository.findByCustomerCustomerIdAndBillStatusStatusNot(customer.getCustomerId(), Constants.BILL_STATUS_FULL_PAID);

		if(transactionInfo.getTransId() <= 0) {
			transactionInfo = transactionInfoRepository.save(transactionInfo);			
		}
		adjustCustomerBills(customerBills, transactionInfo);
		customerBills = billRepository.findByCustomerCustomerId(customer.getCustomerId());
		return customerBills;
	}

	@Override
	public List<Bill> saveShopTransaction(TransactionInfo transactionInfo) {
		Customer customer = transactionInfo.getCustomer();
		Shop shop = customerService.getCustomer(customer.getCustomerId()).getShop();
		List<Bill> shopBills = billRepository.findByCustomerShopShopIdAndBillStatusStatusNot(shop.getShopId(), Constants.BILL_STATUS_FULL_PAID);

		if(transactionInfo.getTransId() <= 0) {
			transactionInfo = transactionInfoRepository.save(transactionInfo);			
		}
		adjustCustomerBills(shopBills, transactionInfo);
		shopBills = billRepository.findByCustomerShopShopId(shop.getShopId());
		return shopBills;
	}
	/*@Override
	public TransactionInfo saveTransactionEntity(TransactionInfo transactionInfo) {

		if(transactionInfo.getTransId() <= 0) {
			return transactionInfoRepository.save(transactionInfo);			
		}
		return transactionInfo;
	}*/

	@Override
	public BillPayment settleTransaction(Bill bill, TransactionInfo transactionInfo) {
		BillPayment payment = getBillSettlementPayment(transactionInfo, bill, transactionInfo.getPaymentAmount() - transactionInfo.getAdjustedAmount());
		return billPaymentRepository.save(payment);
	}
	private void adjustCustomerBills(List<Bill> customerBills, TransactionInfo transactionInfo) {
		
		double amountToSettle = transactionInfo.getPaymentAmount() - transactionInfo.getAdjustedAmount();
		
		for(Bill bill: customerBills) {
			if(amountToSettle <= 0) {
				return;
			}
			BillPayment payment = getBillSettlementPayment(transactionInfo, bill, amountToSettle);
			
			amountToSettle -= payment != null ? payment.getPaymentAmount() : 0;//billAmountToAdjust;

			billRepository.save(bill);
			billPaymentRepository.save(payment);
		}
	}
	private BillPayment getBillSettlementPayment(TransactionInfo transactionInfo, Bill bill, double amountToSettle) {
		double billAmountToAdjust;
		BillStatus newStatus = null;
		BillPayment payment = new BillPayment();
		
		payment.setTransactionInfo(transactionInfo);
		payment.setCurrentDueAmount(amountToSettle);
		payment.setTotalDueAmount(bill.getTotalAmount());
		payment.setPayeeName(transactionInfo.getPayeeName());
		payment.setPaymentDate(transactionInfo.getPaymentDate());
		payment.setBill(bill);
		
		billAmountToAdjust = bill.getTotalAmount() - bill.getPaidAmount();

		Optional<BillStatus> billStatusOptional = billAmountToAdjust <= amountToSettle ? billStatusRepository.findById(Constants.BILL_STATUS_FULL_PAID) : billStatusRepository.findById(Constants.BILL_STATUS_PARTIAL_PAID);
		if(billStatusOptional.isPresent()) {
			payment.setBillStatus(billStatusOptional.get());				
		}

		if(billAmountToAdjust > amountToSettle) {
			billAmountToAdjust = amountToSettle;
		}
		
		bill.setBillStatus(payment.getBillStatus());
		bill.setPaidAmount(bill.getPaidAmount() + billAmountToAdjust);
		bill.setDueAmount(bill.getDueAmount() - billAmountToAdjust);

		payment.setPaymentAmount(billAmountToAdjust);
		payment.setRemainingAmount(bill.getTotalAmount() - bill.getPaidAmount());
		transactionInfo.setAdjustedAmount(transactionInfo.getAdjustedAmount() + billAmountToAdjust);
		String billStatus = transactionInfo.getAdjustedAmount() == transactionInfo.getPaymentAmount() ? Constants.BILL_STATUS_FULL_PAID : Constants.BILL_STATUS_PARTIAL_PAID;
		Optional<BillStatus> status = billStatusRepository.findById(billStatus);
		
		if(status.isPresent()) {
			newStatus = status.get();
		} else {
			newStatus = new BillStatus();
			newStatus.setStatus(billStatus);
			newStatus.setCreatedOn(bill.getBillDate());
			newStatus.setModifiedOn(bill.getBillDate());
			newStatus = billStatusRepository.save(newStatus);
		}
		transactionInfo.setBillStatus(newStatus);
		return payment;
	}
	@Override
	public List<BillStatus> getBillStatuses() {
		return billStatusRepository.findAll();
	}
	@Override
	public BillStatus getBillStatus(String billStatus) {
		Optional<BillStatus> billStatusOptional = billStatusRepository.findById(billStatus);
		if(billStatusOptional.isPresent()){
			return billStatusOptional.get();
		}
		return null;
	}
	@Override
	public BillPayment saveBillPayment(BillPayment billPayment) {
		return billPaymentRepository.save(billPayment);
	}
	@Override
	public List<Bill> getHomeDataBills() {
		return billRepository.findFirst8BillsByOrderByBillDateDesc();
	}
	@Override
	public List<Bill> getShopBills(long shopId) {
		return billRepository.findByCustomerShopShopId(shopId);
	}
	@Override
	public List<Bill> getBillsAfter(String billStatus, Date date) {
		return billRepository.findByBillStatusStatusAndBillDateGreaterThan(billStatus, date);
	}
	@Override
	public List<Bill> getBillsAfter(Date date) {
		return billRepository.findByBillDateGreaterThan(date);
	}
}
