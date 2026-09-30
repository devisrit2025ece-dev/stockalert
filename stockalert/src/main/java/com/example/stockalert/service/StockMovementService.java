package com.example.stockalert.service;

import com.example.stockalert.dto.FastMovingProduct;
import com.example.stockalert.model.Product;
import com.example.stockalert.model.StockMovement;
import com.example.stockalert.repository.ProductRepository;
import com.example.stockalert.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final ReorderAlertService reorderAlertService;

    public StockMovementService(
            StockMovementRepository stockMovementRepository,
            ProductRepository productRepository,
            ReorderAlertService reorderAlertService) {

        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.reorderAlertService = reorderAlertService;
    }

    public StockMovement addMovement(StockMovement movement) {

        Product product = productRepository.findById(movement.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (movement.getType().equalsIgnoreCase("OUT")) {

            product.setCurrentStock(
                    product.getCurrentStock() - movement.getQuantity()
            );

        } else if (movement.getType().equalsIgnoreCase("IN")) {

            product.setCurrentStock(
                    product.getCurrentStock() + movement.getQuantity()
            );
        }

        productRepository.save(product);

        reorderAlertService.checkAndCreateAlert(product.getId());

        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getSalesBetween(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return stockMovementRepository
                .findByTypeAndMovementDateBetween(
                        "OUT",
                        startDate,
                        endDate
                );
    }

    // Find fast-moving products
    public List<FastMovingProduct> getFastMovingProducts(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        List<StockMovement> sales =
                stockMovementRepository.findByTypeAndMovementDateBetween(
                        "OUT",
                        startDate,
                        endDate
                );

        List<FastMovingProduct> result = new ArrayList<>();

        for (Product product : productRepository.findAll()) {

            int totalSales = 0;

            for (StockMovement movement : sales) {

                if (movement.getProductId().equals(product.getId())) {
                    totalSales += movement.getQuantity();
                }
            }

            if (totalSales > 0) {
                result.add(
                        new FastMovingProduct(
                                product.getId(),
                                product.getName(),
                                totalSales
                        )
                );
            }
        }

        result.sort(
                (a, b) -> Integer.compare(
                        b.getTotalSales(),
                        a.getTotalSales()
                )
        );

        return result;
    }
}