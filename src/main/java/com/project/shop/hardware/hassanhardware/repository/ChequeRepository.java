package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.Cheque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
	List<Cheque> findFirst5ChequesByChequeCashedOrderByDueDateAsc(String checkCashed);
	List<Cheque> findByChequeCashedAndDueDateLessThan(String checkCashed, Date currentDate);
	List<Cheque> findByChequeCashedAndDueDateGreaterThan(String checkCashed, Date currentDate);
	List<Cheque> findByChequeCashed(String checkCashed);
	List<Cheque> findByCustomerCustomerIdAndChequeCashed(long customerId, String checkCashed);
	List<Cheque> findByCustomerCustomerIdAndChequeCashedOrApproved(long customerId, String optionNo, String optionNo2);
	List<Cheque> findFirst5ChequesByChequeCashedOrApprovedOrderByDueDateAsc(String optionNo, String optionNo2);
	List<Cheque> findByChequeCashedOrApproved(String optionNo, String optionNo2);
	List<Cheque> findByChequeCashedOrderByDueDateAsc(String optionYes);
	
    @Query("SELECT ch FROM Cheque ch WHERE ch.customer.customerId = :customerId AND (ch.chequeCashed = :checkCashed OR ch.approved = :approved)")
    List<Cheque> findByCustomerIdAndChequeCashedOrApproved(@Param("customerId") long customerId, @Param("checkCashed") String checkCashed, @Param("approved") String approved);

}
