package com.example.productsearch.model;

import jakarta.validation.constraints.NotNull;

public class OrderInput {
    @NotNull private Integer productId;
    @NotNull private Integer count;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    // Getters and setters
}