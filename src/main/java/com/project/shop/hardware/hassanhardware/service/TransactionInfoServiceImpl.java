package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@Service
public class TransactionInfoServiceImpl extends DaoConnectionService implements TransactionInfoService {

	@Override
	public List<TransactionInfo> getCustomerUnsettledTransaction(long customerId) {
		return transactionInfoRepository.findByCustomerCustomerIdAndBillStatusStatusNotAndApproved(customerId, Constants.BILL_STATUS_FULL_PAID, Constants.OPTION_YES);
	}

	@Override
	public List<TransactionInfo> getShopUnsettledTransaction(long shopId) {
		return transactionInfoRepository.findByCustomerShopShopIdAndBillStatusStatusNotAndApproved(shopId, Constants.BILL_STATUS_FULL_PAID, Constants.OPTION_YES);
	}

	@Override
	public List<TransactionInfo> getPaymentsForDate(Date currentDate) {
		return transactionInfoRepository.findByPaymentDateAndPaymentAmountGreaterThan(currentDate, 0);
	}

	@Override
	public List<TransactionInfo> getPayments() {
		return transactionInfoRepository.findByPaymentAmountGreaterThanOrderByPaymentDateDesc(0);
	}

	@Override
	public TransactionInfo getPaymentTransaction(long transId) {
		Optional<TransactionInfo> transactionInfo = transactionInfoRepository.findById(transId);
		if(transactionInfo.isPresent()) {
			return transactionInfo.get();
		}
		return null;
	}

	@Override
	public List<TransactionInfo> getCustomerPendingTransactions(long customerId) {
		return transactionInfoRepository.findByCustomerCustomerIdAndApproved(customerId, Constants.OPTION_NO);
	}
	@Override
	public List<TransactionInfo> getCustomerExtraTransactions(long customerId) {
		return transactionInfoRepository.findByCustomerCustomerIdAndApprovedAndBillStatusStatusNot(customerId, Constants.OPTION_YES, Constants.BILL_STATUS_FULL_PAID);
	}
	@Override
	public TransactionInfo saveTransactionEntity(TransactionInfo transactionInfo) {

		if(transactionInfo.getTransId() <= 0) {
			return transactionInfoRepository.save(transactionInfo);			
		}
		return transactionInfo;
	}

	@Override
	public List<TransactionInfo> getPendingUserTransactions(User userEntity) {
		return transactionInfoRepository.findByUserUserId(userEntity.getUserId());
	}
}
