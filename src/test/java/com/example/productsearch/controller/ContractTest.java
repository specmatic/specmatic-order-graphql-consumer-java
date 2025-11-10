package com.example.productsearch.controller;

import io.specmatic.graphql.VersionInfo;
import io.specmatic.graphql.stub.GraphQLStub;
import io.specmatic.test.SpecmaticContractTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractTest implements SpecmaticContractTest {
    public static final String APPLICATION_HOST = "localhost";
    public static final String APPLICATION_PORT = "8070";
    private static GraphQLStub graphQLStub;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Testing using Specmatic GraphQL " + VersionInfo.INSTANCE.describe());
        System.setProperty("host", APPLICATION_HOST);
        System.setProperty("port", APPLICATION_PORT);
        System.setProperty("SPECMATIC_GENERATIVE_TESTS", "true");

        ArrayList<String> examplesDirectoryList = new ArrayList<>() {{
            add("src/test/resources/specmatic/graphql/examples");
        }};

        graphQLStub = GraphQLStub.createGraphQLStub(examplesDirectoryList, APPLICATION_HOST, 8080);
    }

    @AfterAll
    public static void afterAll() {
        if(graphQLStub != null)
            graphQLStub.close();
    }
}
