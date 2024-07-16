# Create a new product

Request

```graphql
mutation {
    createProduct(newProduct: {
        name: "The Almanac",
        inventory: 10,
        type: book
    }) { id }
}
```

Response

```json
{
  "id": "10"
}
```
