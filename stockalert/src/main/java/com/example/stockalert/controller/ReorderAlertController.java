package com.example.stockalert.controller;

import com.example.stockalert.model.ReorderAlert;
import com.example.stockalert.repository.ReorderAlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reorder-alerts")
public class ReorderAlertController {

    private final ReorderAlertRepository reorderAlertRepository;

    public ReorderAlertController(ReorderAlertRepository reorderAlertRepository) {
        this.reorderAlertRepository = reorderAlertRepository;
    }

    @GetMapping
    public List<ReorderAlert> getAllAlerts() {
        return reorderAlertRepository.findAll();
    }

    @PostMapping("/{id}/close")
    public ReorderAlert closeAlert(@PathVariable Long id) {

        ReorderAlert alert = reorderAlertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus("CLOSED");

        return reorderAlertRepository.save(alert);
    }
}