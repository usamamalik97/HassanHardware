package com.project.shop.hardware.hassanhardware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.project.shop.hardware.hassanhardware.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findTop8ByOrderByCustomerId();
    List<Customer> findByShopShopId(long shopId);
    List<Customer> findByCustomerNameContainingIgnoreCase(String keyword);
    @Query("SELECT c FROM Customer c WHERE c.customerName LIKE %:searchString% OR c.shop.shopName LIKE %:searchString%")
    List<Customer> findByCustomerNameOrShopNameContainingIgnoreCase(@Param("searchString") String keyword);

}
