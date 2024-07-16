package com.example.productsearch.controller;

import in.specmatic.graphql.stub.GraphQLStub;
import in.specmatic.test.SpecmaticContractTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractTest implements SpecmaticContractTest {
    private static GraphQLStub graphQLStub;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("host", "localhost");
        System.setProperty("port", "8070");

        ArrayList<String> stubDirectories = new ArrayList<>() {{
            add("src/test/resources/specmatic/graphql/examples");
        }};

        graphQLStub = GraphQLStub.createGraphQLStub(stubDirectories, "localhost", 8080);
    }

    @AfterAll
    public static void afterAll() {
        if(graphQLStub != null)
            graphQLStub.close();
    }
}
