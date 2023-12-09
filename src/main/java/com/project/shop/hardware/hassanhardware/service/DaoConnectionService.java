package com.project.shop.hardware.hassanhardware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.repository.BillPaymentRepository;
import com.project.shop.hardware.hassanhardware.repository.BillRepository;
import com.project.shop.hardware.hassanhardware.repository.BillStatusRepository;
import com.project.shop.hardware.hassanhardware.repository.ChequeRepository;
import com.project.shop.hardware.hassanhardware.repository.CustomerRepository;
import com.project.shop.hardware.hassanhardware.repository.DailyExpenseRepository;
import com.project.shop.hardware.hassanhardware.repository.ExpenseTypeRepository;
import com.project.shop.hardware.hassanhardware.repository.ItemRecordRepository;
import com.project.shop.hardware.hassanhardware.repository.ItemRepository;
import com.project.shop.hardware.hassanhardware.repository.ShopRepository;
import com.project.shop.hardware.hassanhardware.repository.ShopTypeRepository;
import com.project.shop.hardware.hassanhardware.repository.SoldItemsRepository;
import com.project.shop.hardware.hassanhardware.repository.TransactionInfoRepository;
import com.project.shop.hardware.hassanhardware.repository.UserRepository;

/*import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import jakarta.persistence.EntityManager;*/

@Scope("singleton")
@Service
public class DaoConnectionService {
	@Autowired
	protected BillRepository billRepository = null;
	
	@Autowired
	protected ShopRepository shopRepository;
	
	@Autowired
	protected CustomerRepository customerRepository;
	
	@Autowired
	protected BillStatusRepository billStatusRepository;

	@Autowired
	protected BillPaymentRepository billPaymentRepository;

	@Autowired
	protected TransactionInfoRepository transactionInfoRepository;

	@Autowired
	protected ItemRepository itemRepository;

	@Autowired
	protected ItemRecordRepository itemRecordRepository;

	@Autowired
	protected SoldItemsRepository soldItemsRepository;
	
	@Autowired
	protected ShopTypeRepository shopTypeRepository;
	
	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ChequeRepository chequeRepository;

	@Autowired
	protected DailyExpenseRepository dailyExpenseRepository;

	@Autowired
	protected ExpenseTypeRepository expenseTypeRepository;

}
