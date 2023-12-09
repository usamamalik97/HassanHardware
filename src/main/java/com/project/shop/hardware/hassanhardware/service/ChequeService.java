package com.project.shop.hardware.hassanhardware.service;

import java.util.List;
import java.util.Map;

import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.User;

public interface ChequeService {

	void saveCheque(Cheque chequeEntity);

	Cheque getCheque(long chequeId);

	List<Cheque> getHomeDataCheques();

	Map<String,Object> getCheques();

	List<Cheque> getCustomerPendingCheques(long customerId);

	List<Cheque> getPendingUserCheques(User userEntity);

}
