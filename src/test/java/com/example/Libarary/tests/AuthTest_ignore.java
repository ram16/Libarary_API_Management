package com.example.Libarary.tests;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static org.testng.Assert.assertNotNull;

public class AuthTest_ignore {
    @Test
    public void testLoginAndGetToken() {
        RestAssured.baseURI = "https://librarymanagementapisystem.onrender.com"; // <-- set base URI

        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\"username\": \"RamuP\", \"password\": \"Secpass\"}")
                .post("/member/login");

        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        assertNotNull(token, "Token should not be null");
    }
}