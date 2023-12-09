package com.project.shop.hardware.hassanhardware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.BillPayment;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
	List<BillPayment> findByBillBillId(long billId);
}
