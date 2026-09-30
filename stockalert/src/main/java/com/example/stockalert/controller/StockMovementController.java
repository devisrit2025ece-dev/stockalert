package com.example.stockalert.controller;

import com.example.stockalert.dto.FastMovingProduct;
import com.example.stockalert.model.StockMovement;
import com.example.stockalert.repository.StockMovementRepository;
import com.example.stockalert.service.StockMovementService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stock-movements")
public class StockMovementController {

    private final StockMovementRepository stockMovementRepository;
    private final StockMovementService stockMovementService;

    public StockMovementController(
            StockMovementRepository stockMovementRepository,
            StockMovementService stockMovementService) {

        this.stockMovementRepository = stockMovementRepository;
        this.stockMovementService = stockMovementService;
    }

    @GetMapping
    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    @PostMapping
    public StockMovement addMovement(@RequestBody StockMovement movement) {

        movement.setMovementDate(LocalDateTime.now());

        return stockMovementService.addMovement(movement);
    }

    @GetMapping("/sales")
    public List<StockMovement> getSalesBetween(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return stockMovementService.getSalesBetween(
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate)
        );
    }

    // Get fast-moving products
    @GetMapping("/fast-moving")
    public List<FastMovingProduct> getFastMovingProducts(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return stockMovementService.getFastMovingProducts(
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate)
        );
    }
}