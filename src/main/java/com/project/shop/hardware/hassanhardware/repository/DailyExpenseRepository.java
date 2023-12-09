package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.DailyExpense;

@Repository
public interface DailyExpenseRepository extends JpaRepository<DailyExpense, Long> {
	List<DailyExpense> findByDate(Date date);
	List<DailyExpense> findAllByOrderByDateDesc();
	List<DailyExpense> findByExpenseTypeExpenseTypeContainingIgnoreCaseOrderByDateDesc(String searchText);
	List<DailyExpense> findFirst5DailyExpensesByOrderByDateDesc();
}
