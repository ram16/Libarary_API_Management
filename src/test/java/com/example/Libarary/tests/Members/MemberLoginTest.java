package com.example.Libarary.tests.Members;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestBase;

import static utils.TestBase.token;

public class MemberLoginTest extends TestBase {

    @Test
    public void testLoginSuccess() {
        // Token is already fetched in TestBase.setup()

        System.out.println("Using token from TestBase: " + token);
        Assert.assertNotNull(token, "Token should not be null for successful login");
    }

    @Test
    public void testLoginFailureInvalidUser() {
        String invalidRequestBody = "{\"username\": \"invalidUser\"}";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(invalidRequestBody)
                .post("/member/login");

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertTrue(statusCode == 401 || statusCode == 500, "Expected 401 or 500 status code");
    }
}
