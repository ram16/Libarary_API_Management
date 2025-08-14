package com.example.Libarary.tests.Books;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestBase;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class BookAPITest extends TestBase {

    @Test
    public void getAllBooks() {
        Response response = RestAssured.given()
                .accept("*/*")
                .get("/books/all");

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        if (statusCode == 200) {
            Assert.assertEquals(statusCode, 200, "Status code should be 200");
            Assert.assertFalse(responseBody.isEmpty(), "Response body should not be empty");
        } else if (statusCode == 500) {
            Assert.assertEquals(statusCode, 500, "Status code should be 500");
            String errorMessage = response.jsonPath().getString("error");
            Assert.assertEquals(errorMessage, "Error fetching books", "Error message mismatch");
        } else {
            Assert.fail("Unexpected response status: " + statusCode);
        }
    }

    @Test
    public void testGetAllBooks_ServerError() {
        Response response = RestAssured.given()
                .accept("*/*")
                .get("/books/all");

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 500,
                "Status code should be 200 or 500 but was " + statusCode);
    }

    @Test
    public void testGetBookByTitle_Success() {
        String title = "The Very Busy Spider";

        Response response = RestAssured.given()
                .accept("application/json")
                .pathParam("title", title)
                .when()
                .get("/books/title/{title}");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        List<Map<String, Object>> books = response.jsonPath().getList("");
        Assert.assertFalse(books.isEmpty(), "Books list should not be empty");

        boolean found = books.stream()
                .anyMatch(book -> title.equals(book.get("title")));
        Assert.assertTrue(found, "At least one book should have the title: " + title);
    }

    @Test
    public void testGetBooksByAuthor_Success() {
        String author = "Eric Carle";

        Response response = RestAssured.given()
                .accept("application/json")
                .pathParam("author", author)
                .when()
                .get("/books/author/{author}");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        List<Map<String, Object>> books = response.jsonPath().getList("");
        Assert.assertFalse(books.isEmpty(), "Books list should not be empty");

        boolean found = books.stream()
                .anyMatch(book -> author.equalsIgnoreCase((String) book.get("author")));
        Assert.assertTrue(found, "At least one book should have the author: " + author);
    }


    @Test
    public void testGetBookAvailability() {
        String title = "The Snowman";

        Response response = RestAssured.given()
                .spec(requestSpec)
                .accept("application/json")
                .queryParam("title", title)
                .log().uri()   // logs the full request URI with encoded placeholder
                .when()
                .get("/books/availability/%7Btitle%7D");  // manually put encoded {title} here

        // assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertEquals(response.jsonPath().getString("title"), title, "Title should match");
        Assert.assertTrue(response.jsonPath().getInt("availableCopies") >= 0, "Available copies should be >= 0");
    }



    @Test
    public void testGetBookAvailability_Suc() {
        String title = "The Snowman";

        Response response = RestAssured.given()
                .spec(requestSpec)
                .accept("application/json")
                .queryParam("title", title)
                .log().uri()
                .when()
                .get("/books/availability/%7Btitle%7D");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertEquals(response.jsonPath().getString("title"), title, "Title should match");
        Assert.assertTrue(response.jsonPath().getInt("availableCopies") >= 0, "Available copies should be >= 0");
    }

    @Test
    public void testGetBookAvailability_BadRequest() {
        // No title provided
        Response response = RestAssured.given()
                .spec(requestSpec)
                .accept("application/json")
                .log().uri()
                .when()
                .get("/books/availability/%7Btitle%7D");

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        Assert.assertEquals(response.jsonPath().getString("error"), "Title is required", "Error message mismatch");
    }

    @Test
    public void testGetBookAvailability_NotFound() {
        String fakeTitle = "ccc";

        Response response = RestAssured.given()
                .spec(requestSpec)
                .accept("application/json")
                .queryParam("title", fakeTitle)
                .log().uri()
                .when()
                .get("/books/availability/%7Btitle%7D");

        Assert.assertEquals(response.getStatusCode(), 404, "Status code should be 404");
        Assert.assertEquals(response.jsonPath().getString("error"), "Book not found", "Error message mismatch");
    }

    @Test
    public void testGetBookAvailability_ServerError() {
        String triggerTitle = "trigger500";

        Response response = RestAssured.given()
                .spec(requestSpec)
                .accept("application/json")
                .queryParam("title", triggerTitle)
                .log().uri()
                .when()
                .get("/books/availability/%7Btitle%7D");

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 500, "Status code should be 200 or 500");

        if (statusCode == 500) {
            Assert.assertEquals(response.jsonPath().getString("error"), "Error fetching book availability", "Error message mismatch");
        }
    }

  }