package com.example.productsearch.controller;

import com.example.productsearch.model.Product;
import com.example.productsearch.model.NewProductInput;
import com.example.productsearch.model.OrderInput;
import com.example.productsearch.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findAvailableProducts")
    public ResponseEntity<List<Product>> findAvailableProducts(
            @RequestParam(required = false) String type,
            @RequestHeader(required = true) Integer pageSize) {
        List<Product> products = productService.findAvailableProducts(type, pageSize);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<Map<String, Integer>> createProduct(@Valid @RequestBody NewProductInput newProduct) {
        Integer createdProductId = productService.createProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", createdProductId));
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Integer>> createOrder(@Valid @RequestBody OrderInput orderInput) {
        Integer createdOrderId = productService.createOrder(orderInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", createdOrderId));
    }
}