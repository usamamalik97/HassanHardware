package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.DailyExpense;
import com.project.shop.hardware.hassanhardware.entity.ExpenseType;

@Service
public class ExpenseServiceImpl extends DaoConnectionService implements ExpenseService {

	@Override
	public List<DailyExpense> getExpenses(Date date) {
		return dailyExpenseRepository.findByDate(date);
	}

	@Override
	public List<DailyExpense> getExpenses() {
		return dailyExpenseRepository.findAllByOrderByDateDesc();
	}

	@Override
	public ExpenseType getExpenseType(String expenseType) {
		Optional<ExpenseType> expenseTypeEntity = expenseTypeRepository.findById(expenseType);
		if(expenseTypeEntity.isPresent()) {
			return expenseTypeEntity.get();
		}
		return null;
	}

	@Override
	public DailyExpense saveExpense(DailyExpense expense) {
		return dailyExpenseRepository.save(expense);
	}

	@Override
	public List<DailyExpense> getExpensesLike(String searchText) {
		return dailyExpenseRepository.findByExpenseTypeExpenseTypeContainingIgnoreCaseOrderByDateDesc(searchText);
	}

	@Override
	public List<ExpenseType> getExpenseTypes() {
		return expenseTypeRepository.findAll();
	}

	@Override
	public List<DailyExpense> getHomeDataExpenses() {
		return dailyExpenseRepository.findFirst5DailyExpensesByOrderByDateDesc();
	}

}
