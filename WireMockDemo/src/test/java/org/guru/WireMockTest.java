package org.guru;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class WireMockTest {

   // RestAssured.baseURI = "http://test.com";
   @BeforeAll
   public static void setupRestAssured() {
       RestAssured.port = 8080;
   }

    @Test
    public void TestgetBooks(){
            // Perform a GET request to the mocked endpoint
            RestAssured.given()
                    .when()
                    .get("/api/books")
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("$", hasSize(2)) // Assert that the response contains 2 items
                    .body("[0].id", equalTo(1)) // Assert the first item's id
                    .body("[0].title", equalTo("Book 1")); // Assert the first item's title
    }
}



