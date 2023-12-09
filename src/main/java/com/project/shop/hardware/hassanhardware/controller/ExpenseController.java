package com.project.shop.hardware.hassanhardware.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.beans.DailyExpenseBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.DailyExpense;
import com.project.shop.hardware.hassanhardware.entity.ExpenseType;
import com.project.shop.hardware.hassanhardware.service.ExpenseService;

@CrossOrigin
@RestController
public class ExpenseController {

	@Autowired
	private RequestMapper mapper;
	
	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping("/hassan-hardware/expenses/add")
	public List<DailyExpense> addExpenses(@RequestParam("Expense") String dailyExpense) {
		DailyExpenseBean dailyExpenseBean = mapper.mapValues(dailyExpense, DailyExpenseBean.class);
		DailyExpense expense = dailyExpenseBean.getEntityObject();
		Date currentDate = new Date(new java.util.Date().getTime());
		expense.setDate(currentDate);
		expense.setExpenseType(expenseService.getExpenseType(dailyExpenseBean.getExpenseType()));
		expenseService.saveExpense(expense);
		return expenseService.getExpenses();
	}
	@GetMapping("/hassan-hardware/expense-types/")
	public List<ExpenseType> getExpenseTypes() {
		return expenseService.getExpenseTypes();
	}
	@GetMapping("/hassan-hardware/expenses/")
	public List<DailyExpense> getExpenses() {
		return expenseService.getExpenses();
	}
	@GetMapping("/hassan-hardware/expenses/{date}/")
	public List<DailyExpense> getExpenses(@PathVariable("date") Date date) {
		return expenseService.getExpenses(date);
	}
	@GetMapping("/hassan-hardware/expense/{searchText}/")
    public List<DailyExpense> getExpenses(@PathVariable("searchText") String searchText) {
		return expenseService.getExpensesLike(searchText);
    }
}
