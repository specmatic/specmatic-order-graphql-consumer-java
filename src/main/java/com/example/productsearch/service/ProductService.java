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

    public static final String FIND_AVAILABLE_PRODUCTS = """
                query ($type: ProductType!, $pageSize: Int) {
                    findAvailableProducts(type: $type, pageSize: $pageSize) {
                        id
                        name
                        inventory
                        type
                    }
                }
            """;
    public static final String CREATE_PRODUCT = """
            mutation {
                createProduct(newProduct: {
                    name: "%s",
                    inventory: %d,
                    type: %s
                }) {
                    id
                }
            }
            """;
    public static final String CREATE_ORDER = """
            mutation {
                createOrder(order: {
                    productId: %d,
                    count: %d
                }) {
                    id
                }
            }
            """;
    private final HttpGraphQlClient graphQlClient;

    public ProductService(HttpGraphQlClient graphQlClient) {
        this.graphQlClient = graphQlClient;
    }

    public List<Product> findAvailableProducts(String type, Integer pageSize) {
        String query = FIND_AVAILABLE_PRODUCTS;

        Map<String, Object> variables = new HashMap<>();
        variables.put("type", type);
        variables.put("pageSize", pageSize != null ? pageSize : 10); // Default to 10

        return graphQlClient
                .mutate()
                .header("X-region", "north-west")
                .build()
                .document(query)
                .variables(variables)
                .retrieve("findAvailableProducts")
                .toEntityList(Product.class)
                .block();
    }
    public String createProduct(NewProductInput newProduct) {
        String mutation = String.format(CREATE_PRODUCT, newProduct.getName(), newProduct.getInventory(), newProduct.getType().toString());
        return graphQlClient.document(mutation)
                .retrieve("createProduct.id")
                .toEntity(String.class)
                .block();
    }

    public String createOrder(OrderInput orderInput) {
        String mutation = String.format(CREATE_ORDER, orderInput.getProductId(), orderInput.getCount());
        return graphQlClient.document(mutation)
                .retrieve("createOrder.id")
                .toEntity(String.class)
                .block();
    }
}