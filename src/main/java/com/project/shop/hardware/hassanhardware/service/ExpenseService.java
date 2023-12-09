package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;

import com.project.shop.hardware.hassanhardware.entity.DailyExpense;
import com.project.shop.hardware.hassanhardware.entity.ExpenseType;

public interface ExpenseService {

	List<DailyExpense> getExpenses(Date date);

	List<DailyExpense> getExpenses();

	ExpenseType getExpenseType(String expenseType);

	DailyExpense saveExpense(DailyExpense expense);

	List<DailyExpense> getExpensesLike(String searchText);

	List<ExpenseType> getExpenseTypes();

	List<DailyExpense> getHomeDataExpenses();

}
