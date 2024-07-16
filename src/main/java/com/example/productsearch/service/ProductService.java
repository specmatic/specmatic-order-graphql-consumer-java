package com.example.productsearch.service;

import com.example.productsearch.model.Product;
import com.example.productsearch.model.NewProductInput;
import com.example.productsearch.model.OrderInput;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final HttpGraphQlClient graphQlClient;

    public ProductService(HttpGraphQlClient graphQlClient) {
        this.graphQlClient = graphQlClient;
    }

    public List<Product> findAvailableProducts(String type, Integer pageSize) {
        String query = String.format("""
            query {
                findAvailableProducts(type: %s, pageSize: %d) {
                    id
                    name
                    inventory
                    type
                }
            }
            """, type, pageSize);

        return graphQlClient.document(query)
                .retrieve("findAvailableProducts")
                .toEntityList(Product.class)
                .block();
    }

    public Integer createProduct(NewProductInput newProduct) {
        String mutation = String.format("""
            mutation {
                createProduct(newProduct: {
                    name: "%s",
                    inventory: %d,
                    type: %s
                }) {
                    id
                }
            }
            """, newProduct.getName(), newProduct.getInventory(), newProduct.getType().toString());

        return graphQlClient.document(mutation)
                .retrieve("createProduct.id")
                .toEntity(Integer.class)
                .block();
    }

    public Integer createOrder(OrderInput orderInput) {
        String mutation = String.format("""
            mutation {
                createOrder(order: {
                    productId: %d,
                    count: %d
                }) {
                    id
                }
            }
            """, orderInput.getProductId(), orderInput.getCount());

        return graphQlClient.document(mutation)
                .retrieve("createOrder.id")
                .toEntity(Integer.class)
                .block();
    }
}