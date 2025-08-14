package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertNotNull;

public class TestBase {

    public static String token;
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {  // remove static here, TestNG @BeforeClass shouldn't be static
        RestAssured.baseURI = "https://librarymanagementapisystem.onrender.com";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\": \"RamuP\", \"password\": \"Secpass\"}")
                .post("/member/login");

        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        assertNotNull(token, "Token should not be null");

        // Build reusable request specification with auth header and content type
        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setContentType("application/json")
                .build();
    }
}
