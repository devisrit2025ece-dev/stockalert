package com.example.stockalert.repository;

import com.example.stockalert.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByTypeAndMovementDateBetween(
            String type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}