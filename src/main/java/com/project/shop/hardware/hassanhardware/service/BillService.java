package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.BillPayment;
import com.project.shop.hardware.hassanhardware.entity.BillStatus;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;

public interface BillService {

	Bill saveBill(Bill bill);

	BillStatus saveBillStatus(BillStatus billStatus);

	List<Bill> getBills();

	Bill getBill(long billId);

	List<Bill> getBills(String billStatus);

	List<Bill> getBillsExcept(String billStatusFullPaid);

	List<BillPayment> getBillPayments(long billId);

	BillPayment getBillPayment(long billId, long paymentId);

	List<Bill> saveTransaction(TransactionInfo transactionInfo);
	List<Bill> saveShopTransaction(TransactionInfo transactionInfo);
	//TransactionInfo saveTransactionEntity(TransactionInfo transactionInfo);

	List<BillStatus> getBillStatuses();
	
	BillStatus getBillStatus(String billStatus);

	BillPayment saveBillPayment(BillPayment billPayment);
	BillPayment settleTransaction(Bill bill, TransactionInfo transactionInfo);

	List<Bill> getHomeDataBills();

	List<Bill> getShopBills(long shopId);

	List<Bill> getBillsAfter(String billStatusFullPaid, Date date);

	List<Bill> getBillsAfter(Date date);

}
