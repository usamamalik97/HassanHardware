package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;

public interface TransactionInfoRepository  extends JpaRepository<TransactionInfo, Long>{
    List<TransactionInfo> findByCustomerCustomerIdAndBillStatusStatusNot(long customerId, String status);
    List<TransactionInfo> findByCustomerShopShopIdAndBillStatusStatusNot(long customerId, String status);
	List<TransactionInfo> findByPaymentDate(Date currentDate);
	List<TransactionInfo> findByPaymentDateAndPaymentAmountGreaterThan(Date currentDate, double amount);
	List<TransactionInfo> findByPaymentAmountGreaterThan(double amount);
	List<TransactionInfo> findByPaymentAmountGreaterThanOrderByPaymentDateDesc(double amount);
	List<TransactionInfo> findByCustomerCustomerIdAndBillStatusStatusNotAndApproved(long customerId, String billStatusFullPaid, String optionYes);
	List<TransactionInfo> findByCustomerShopShopIdAndBillStatusStatusNotAndApproved(long shopId, String billStatusFullPaid, String optionYes);
	List<TransactionInfo> findByCustomerCustomerIdAndApproved(long customerId, String optionNo);
	List<TransactionInfo> findByPaymentDateAndApprovedAndPaymentAmountGreaterThan(Date currentDate, String optionYes, double amount);
	List<TransactionInfo> findByCustomerCustomerIdAndApprovedAndBillStatusStatusNot(long customerId, String optionYes,
			String billStatusFullPaid);
	List<TransactionInfo> findByUserUserId(long userId);
}
