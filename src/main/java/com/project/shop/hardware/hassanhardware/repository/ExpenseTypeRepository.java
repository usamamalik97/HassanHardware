package com.project.shop.hardware.hassanhardware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.ExpenseType;

@Repository
public interface ExpenseTypeRepository  extends JpaRepository<ExpenseType, String>{

}
