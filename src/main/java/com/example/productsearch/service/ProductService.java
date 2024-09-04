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
                query ($type: ProductType!, $pageSize: Int!) {
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
        variables.put("pageSize", pageSize);

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
    public Integer createProduct(NewProductInput newProduct) {
        String mutation = String.format(CREATE_PRODUCT, newProduct.getName(), newProduct.getInventory(), newProduct.getType().toString());

        return graphQlClient.document(mutation)
                .retrieve("createProduct.id")
                .toEntity(Integer.class)
                .block();
    }

    public Integer createOrder(OrderInput orderInput) {
        String mutation = String.format(CREATE_ORDER, orderInput.getProductId(), orderInput.getCount());

        return graphQlClient.document(mutation)
                .retrieve("createOrder.id")
                .toEntity(Integer.class)
                .block();
    }
}