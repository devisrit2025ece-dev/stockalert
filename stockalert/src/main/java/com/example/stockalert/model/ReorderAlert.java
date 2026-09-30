package com.example.stockalert.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reorder_alerts")
public class ReorderAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String status;

    public ReorderAlert() {
    }

    public ReorderAlert(Long productId, String status) {
        this.productId = productId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}