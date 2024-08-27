package com.example.productsearch.service;

import com.example.productsearch.model.Product;
import com.example.productsearch.model.NewProductInput;
import com.example.productsearch.model.OrderInput;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final HttpGraphQlClient graphQlClient;

    public ProductService(HttpGraphQlClient graphQlClient) {
        this.graphQlClient = graphQlClient;
    }

    public List<Product> findAvailableProducts(String type, Integer pageSize) {
        // Define the GraphQL query with variables
        String query = """
        query ($type: ProductType!, $pageSize: Int!) {
            findAvailableProducts(type: $type, pageSize: $pageSize) {
                id
                name
                inventory
                type
            }
        }
    """;

        // Define the variables map
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", type);
        variables.put("pageSize", pageSize);

        // Configure and execute the GraphQL client
        return graphQlClient
                .mutate()
                .header("X-region", "north-west")
                .build()
                .document(query)         // Pass the query
                .variables(variables)    // Pass the variables map
                .retrieve("findAvailableProducts")  // Specify the path to retrieve
                .toEntityList(Product.class)  // Convert the result to a list of Products
                .block();  // Block to wait for the result synchronously
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