package com.example.productsearch.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class NewProductInput {
    @NotNull
    private String name;
    @NotNull
    private ProductType type;
    @Min(value = 1)
    @Max(value = 101)
    @NotNull
    private int inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    // Getters and setters
}
