package com.project.shop.hardware.hassanhardware.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.Bill;
import com.project.shop.hardware.hassanhardware.entity.BillStatus;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByBillStatusNot(BillStatus billStatus);
    List<Bill> findFirst8BillsByOrderByBillDateDesc();
    List<Bill> findAllByOrderByBillDateDesc();
    List<Bill> findByCustomerCustomerIdAndBillStatusStatusNot(long customerId, String status);
    List<Bill> findByCustomerCustomerIdAndBillStatusStatus(long customerId, String status);
    List<Bill> findByBillStatusStatus(String status);
    List<Bill> findByCustomerCustomerId(long customerId);
    List<Bill> findByCustomerShopShopId(long shopId);
    List<Bill> findByCustomerShopShopIdAndBillStatusStatusNot(long shopId, String status);
    List<Bill> findByBillStatusStatusAndBillDateGreaterThan(String status, Date date);
    List<Bill> findByBillDateGreaterThan(Date date);
}
