package com.example.stockalert.repository;

import com.example.stockalert.model.ReorderAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReorderAlertRepository extends JpaRepository<ReorderAlert, Long> {

    Optional<ReorderAlert> findByProductIdAndStatus(Long productId, String status);
}