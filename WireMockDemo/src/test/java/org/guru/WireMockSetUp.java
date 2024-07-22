package org.guru;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class WireMockSetUp {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp(){
        wireMockServer= new WireMockServer(wireMockConfig().port(8080));
        wireMockServer.start();
        System.out.println("Wire Mock Server started");

        //stubbing the api endpoint
        // Stubbing the API endpoint
        stubFor(get(urlEqualTo("/api/books"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1, \"title\": \"Book 1\"}, {\"id\": 2, \"title\": \"Book 2\"}]")));
    }

    @Test
    public void TestgetBooks(){
        // Perform a GET request to the mocked endpoint
        System.out.println("In Test Method");
        RestAssured.given()
                .when()
                .get("/api/books")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .body("$", hasSize(2)) // Assert that the response contains 2 items
                .body("[0].id", equalTo(1)) // Assert the first item's id
                .body("[0].title", equalTo("Book 1"))
                .log().all(); // Assert the first item's title
    }

    @AfterAll
    public static void tearDown(){
        wireMockServer.stop();
        System.out.println("Wiremock server stopped");
        }


}
