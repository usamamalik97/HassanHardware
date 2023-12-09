package com.project.shop.hardware.hassanhardware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.hardware.hassanhardware.entity.BillStatus;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatus, String> {

}
