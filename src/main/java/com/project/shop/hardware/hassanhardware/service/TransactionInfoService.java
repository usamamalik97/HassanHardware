package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;

import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;

public interface TransactionInfoService {
	List<TransactionInfo> getCustomerUnsettledTransaction(long customerId);
	List<TransactionInfo> getShopUnsettledTransaction(long shopId);
	List<TransactionInfo> getPaymentsForDate(Date currentDate);
	List<TransactionInfo> getPayments();
	TransactionInfo getPaymentTransaction(long transId);
	List<TransactionInfo> getCustomerPendingTransactions(long customerId);
	TransactionInfo saveTransactionEntity(TransactionInfo transactionInfo);
	List<TransactionInfo> getPendingUserTransactions(User userEntity);
	List<TransactionInfo> getCustomerExtraTransactions(long customerId);
}
