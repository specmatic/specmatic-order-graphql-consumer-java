![Diagram](./GraphQLStubbing.gif)

# Specmatic UI project for GraphQL demo

## Run the tests

```shell
./gradlew clean test
```

Look at the `ContractTest` class to see how the GraphQL dependency has been stubbed out.

### Start the application

1. Checkout the Order API project from [here](https://github.com/znsio/specmatic-order-api-java), and start it up using the instructions in it's README. 
2. Checkout the BFF project from [here](https://github.com/znsio/specmatic-order-bff-graphql-java), and start it up using the instructions in it's README.
3. Execute this command:

```shell
./gradlew bootRun
```
