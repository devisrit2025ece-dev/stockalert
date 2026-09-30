package com.example.stockalert.service;

import com.example.stockalert.model.Product;
import com.example.stockalert.model.ReorderAlert;
import com.example.stockalert.repository.ProductRepository;
import com.example.stockalert.repository.ReorderAlertRepository;
import org.springframework.stereotype.Service;

@Service
public class ReorderAlertService {

    private final ReorderAlertRepository reorderAlertRepository;
    private final ProductRepository productRepository;

    public ReorderAlertService(
            ReorderAlertRepository reorderAlertRepository,
            ProductRepository productRepository) {

        this.reorderAlertRepository = reorderAlertRepository;
        this.productRepository = productRepository;
    }

    public void checkAndCreateAlert(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getCurrentStock() <= product.getReorderThreshold()) {

            boolean alertExists =
                    reorderAlertRepository
                            .findByProductIdAndStatus(productId, "OPEN")
                            .isPresent();

            if (!alertExists) {

                ReorderAlert alert = new ReorderAlert(
                        productId,
                        "OPEN"
                );

                reorderAlertRepository.save(alert);
            }
        }
    }
}