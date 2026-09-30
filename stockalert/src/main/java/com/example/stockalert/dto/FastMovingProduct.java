package com.example.stockalert.dto;

public class FastMovingProduct {

    private Long productId;
    private String productName;
    private int totalSales;

    public FastMovingProduct() {
    }

    public FastMovingProduct(Long productId, String productName, int totalSales) {
        this.productId = productId;
        this.productName = productName;
        this.totalSales = totalSales;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
}
