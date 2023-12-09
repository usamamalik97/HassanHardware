package com.project.shop.hardware.hassanhardware.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.hardware.hassanhardware.beans.ChequeBean;
import com.project.shop.hardware.hassanhardware.beans.UserBean;
import com.project.shop.hardware.hassanhardware.bl.RequestMapper;
import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.TransactionInfo;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.service.BillService;
import com.project.shop.hardware.hassanhardware.service.ChequeService;
import com.project.shop.hardware.hassanhardware.service.TransactionInfoService;
import com.project.shop.hardware.hassanhardware.service.UserService;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@CrossOrigin
@RestController
public class ChequeController {

	@Autowired
	private RequestMapper mapper;

	@Autowired
	private BillService billService;
	
	@Autowired
	private ChequeService chequeService;

	@Autowired
	private TransactionInfoService transactionInfoService;

	@Autowired
	private UserService userService;
	
	@PostMapping("/hassan-hardware/cheque/cashed")
	public ResponseEntity<Map<String,Object>> saveCheque(@RequestParam("Cheque") String cheque, @RequestParam("User") String user) {
		ChequeBean chequeBean = mapper.mapValues(cheque, ChequeBean.class);
		Cheque chequeEntity = chequeService.getCheque(chequeBean.getChequeId());
		Date currentDate = new Date(new java.util.Date().getTime());

		UserBean userBean = mapper.mapValues(user, UserBean.class);
		User userEntity = userService.getUser(userBean.getUsername());
		chequeEntity.setChequeCashed(Constants.OPTION_YES);
		chequeService.saveCheque(chequeEntity);
		
		TransactionInfo transaction = new TransactionInfo();
		transaction.setCustomer(chequeEntity.getCustomer());
		transaction.setPayeeName(chequeEntity.getCustomer().getCustomerName());
		transaction.setPaymentAmount(chequeEntity.getAmount());
		transaction.setPaymentDate(currentDate);
		transaction.setCheque(chequeEntity);
		transaction.setUser(userEntity);
		transaction.setApproved(Constants.OPTION_YES);
		transaction = transactionInfoService.saveTransactionEntity(transaction);
		if(transaction.getAdjustedAmount() < transaction.getPaymentAmount()) {
			billService.saveTransaction(transaction);
		}
		if(transaction.getAdjustedAmount() < transaction.getPaymentAmount()) {
			billService.saveShopTransaction(transaction);
		}

		return ResponseEntity.ok(chequeService.getCheques());
	}
	@PostMapping("/hassan-hardware/cheque/approved")
	public ResponseEntity<Map<String,Object>> saveChequeApproved(@RequestParam("Cheque") String cheque, @RequestParam("User") String user) {
		ChequeBean chequeBean = mapper.mapValues(cheque, ChequeBean.class);
		Cheque chequeEntity = chequeService.getCheque(chequeBean.getChequeId());

		chequeEntity.setApproved(Constants.OPTION_YES);
		chequeService.saveCheque(chequeEntity);

		return ResponseEntity.ok(chequeService.getCheques());
	}
	@GetMapping("/hassan-hardware/cheques/")
	public ResponseEntity<Map<String, Object>> getCheques() {
		return ResponseEntity.ok(chequeService.getCheques());
	}
}
