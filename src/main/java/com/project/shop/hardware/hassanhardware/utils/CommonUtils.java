package com.project.shop.hardware.hassanhardware.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.formula.functions.T;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.DailyExpense;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.service.ExpenseService;

public class CommonUtils {

	public static boolean isNullOrEmptyCollection(List<T> collection) {
		return collection == null || collection.size() <= 0;
	}
	public static List<Map<String, String>> getPaymentStats(List<Bill> bills) {
		List<Map<String, String>> paymentStats = new ArrayList<>();
		Map<String, String> paymentStatsTemp;
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Pending");
		paymentStatsTemp.put("value", String.valueOf(getPendingAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		paymentStatsTemp = new ConcurrentHashMap<>();
		paymentStatsTemp.put("name", "Payments");
		paymentStatsTemp.put("value", String.valueOf(getAdjustedAmount(bills)));
		paymentStats.add(paymentStatsTemp);
		return paymentStats;
	}
	public static Double getPendingAmount(List<Bill> bills) {
		double pendingAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				pendingAmount += bill.getDueAmount();
			}
		}
		return pendingAmount;
	}
	
	public static Double getAdjustedAmount(List<Bill> bills) {
		double adjustedAmount = 0.0;
		if(bills != null && bills.size() > 0) {
			for(Bill bill: bills) {
				adjustedAmount += bill.getPaidAmount();
			}
		}
		return adjustedAmount;
	}

	public static Date addInDate(Date dateTime, int unit, int field) {
		Date newDateTime;
		GregorianCalendar calcDate;
		calcDate = new GregorianCalendar();
		calcDate.setTime(dateTime);
		calcDate.add(field, unit);
		newDateTime = calcDate.getTime();
		return newDateTime;
	}
	public static java.sql.Date convertToSqlDate(Date value) {
        return value != null ? new java.sql.Date(value.getTime()) : null;
    }
	public static boolean isNullOrEmptyString(String bank) {
		return bank == null || bank.trim().length() == 0;
	}
	public static double getPaymentAmount(List<TransactionInfo> transactionInfoList, boolean chequeOnly) {
		double paymentAmount = 0.0;
		if(transactionInfoList!= null) {
			for(TransactionInfo transactionInfo: transactionInfoList) {
				if(chequeOnly && transactionInfo.getCheque() != null) {
					paymentAmount += transactionInfo.getPaymentAmount();
				} else if(!chequeOnly  && transactionInfo.getCheque() == null && Constants.OPTION_YES.equals(transactionInfo.getApproved())) {
					paymentAmount += transactionInfo.getPaymentAmount();					
				}
			}
		}
		return paymentAmount;
	}
	public static double getExpensesAmount(java.sql.Date date, ExpenseService expenseService) {
		double expenseAmount = 0.0;
		List<DailyExpense> dailyExpenses = expenseService.getExpenses(date);
		if(dailyExpenses!= null) {
			for(DailyExpense dailyExpense: dailyExpenses) {
				expenseAmount += dailyExpense.getAmount();			}
		}
		return expenseAmount;
	}
	public static double getPendingTransactionsBalance(List<TransactionInfo> customerPendingTransactions) {
		double balance = 0.0;
		if(customerPendingTransactions != null) {
			for(TransactionInfo transactionInfo: customerPendingTransactions) {
				balance += transactionInfo.getPaymentAmount() - transactionInfo.getAdjustedAmount();
			}
		}
		return balance;
	}
}
