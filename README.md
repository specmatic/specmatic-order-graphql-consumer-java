![Diagram](./GraphQLStubbing.gif)

# GraphQL Service Virtualization Using Specmatic GraphQL

* [Specmatic Website](https://specmatic.io)
* [Specmatic Documentation](https://docs.specmatic.io)

This sample project demonstrates how we can practice contract-driven development and contract testing in a REST (Kotlin) API that depends on an external GraphQL domain service. Here, Specmatic is used to stub calls to domain graphQL API service based on its GraphQL specification, and also to contract test the REST API itself based on its own OpenAPI specification.

## Start the application

1. Checkout the BFF project from [here](https://github.com/specmatic/specmatic-order-bff-graphql-java), and start it up using the instructions in it's README.
2. Execute this command:

- On Unix and Windows Powershell:

```shell
./gradlew bootRun
```

- On Windows CMD Prompt:

```shell
gradlew bootRun
```

## Running the contract tests

Look at the [`ContractTest`](./src/test/java/com/example/productsearch/controller/ContractTest.java) class to see how the GraphQL dependency has been stubbed out.

Execute this command to run the contract tests:

- On Unix and Windows Powershell:

```shell
./gradlew test
```

- On Windows CMD Prompt:

```shell
gradlew test
```

### Running the contract tests manually

#### 1. Start the Specmatic GraphQL mock server

- On Unix and Windows Powershell:

```shell
docker run --rm -p 8080:8080 -v "$(pwd)/specmatic.yml:/usr/src/app/specmatic.yml"  -v "$(pwd)/src/test/resources/specmatic/graphql/examples:/usr/src/app/examples" specmatic/specmatic-graphql virtualize --port=8080 --examples=examples
```

- On Windows CMD Prompt:
```shell
docker run --rm -p 8080:8080 -v "%cd%/specmatic.yml:/usr/src/app/specmatic.yml" -v "%cd%/src/test/resources/specmatic/graphql/examples:/usr/src/app/examples" specmatic/specmatic-graphql virtualize --port=8080 --examples=examples
```

#### 2. Build and run the BFF service (System Under Test) using Gradle

- On Unix and Windows Powershell:

```shell
./gradlew bootRun
```

- On Windows CMD Prompt:

```shell
gradlew bootRun
```

#### 3. Run the contract tests using Docker

- On Unix and Windows Powershell:

```shell
docker run --rm --network host -v "$(pwd)/specmatic.yml:/usr/src/app/specmatic.yml" -v "$(pwd)/build/reports/specmatic:/usr/src/app/build/reports/specmatic" specmatic/specmatic test --port=8070
```

- On Windows CMD Prompt:

```shell
docker run --rm --network host -v "%cd%/specmatic.yml:/usr/src/app/specmatic.yml" -v "%cd%/build/reports/specmatic:/usr/src/app/build/reports/specmatic" specmatic/specmatic test --port=8070
```