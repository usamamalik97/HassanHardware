package com.project.shop.hardware.hassanhardware.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.project.shop.hardware.hassanhardware.entity.Cheque;
import com.project.shop.hardware.hassanhardware.entity.User;
import com.project.shop.hardware.hassanhardware.utils.Constants;

@Service
public class ChequeServiceImpl extends DaoConnectionService implements ChequeService{

	@Override
	public void saveCheque(Cheque chequeEntity) {
		chequeRepository.save(chequeEntity);
	}

	@Override
	public Cheque getCheque(long chequeId) {
		Optional<Cheque> cheque = chequeRepository.findById(chequeId);
		if(cheque.isPresent()) {
			return cheque.get();
		}
		return null;
	}
	@Override
	public Map<String,Object> getCheques() {

		List<Cheque> pendingCheques = chequeRepository.findByChequeCashedOrApproved(Constants.OPTION_NO, Constants.OPTION_NO);
		List<Cheque> cashedCheques = chequeRepository.findByChequeCashedOrderByDueDateAsc(Constants.OPTION_YES);
		Map<String,Object> responseMap = new ConcurrentHashMap<>();
		responseMap.put("Pending", pendingCheques);
		responseMap.put("Cashed", cashedCheques);
		return responseMap;
	}
	@Override
	public List<Cheque> getHomeDataCheques() {
		return chequeRepository.findFirst5ChequesByChequeCashedOrApprovedOrderByDueDateAsc(Constants.OPTION_NO, Constants.OPTION_NO);
	}

	@Override
	public List<Cheque> getCustomerPendingCheques(long customerId) {
		return chequeRepository.findByCustomerIdAndChequeCashedOrApproved(customerId, Constants.OPTION_NO, Constants.OPTION_NO);
	}

	@Override
	public List<Cheque> getPendingUserCheques(User userEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
