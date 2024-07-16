package com.example.productsearch.controller;

import com.example.productsearch.model.Product;
import com.example.productsearch.model.NewProductInput;
import com.example.productsearch.model.ProductType;
import com.example.productsearch.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testFindAvailableProducts() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setInventory(10);
        product.setType(ProductType.gadget);

        when(productService.findAvailableProducts(any(), any())).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/findAvailableProducts")
                        .param("type", "gadget")
                        .header("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("New Product");
        product.setInventory(5);
        product.setType(ProductType.book);

        when(productService.createProduct(any())).thenReturn(1);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\",\"inventory\":5,\"type\":\"book\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"));
    }
}