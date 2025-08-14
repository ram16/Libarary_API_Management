package com.example.Libarary.tests.Transactions;

import com.example.Libaray.pojo.TransactionRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;
import utils.TestBase;



public class TransactionsApiTest extends TestBase {

   @Test
   public void testBorrowBook_Success() {
       TransactionRequest borrowRequest = new TransactionRequest("Chicka Chicka Boom Boom", "RamuP");

       given()
               .spec(requestSpec)
               .body(borrowRequest)
               .when()
               .post("/transactions/borrow")
               .then()
               .statusCode(201)
               .body("message", equalTo("Book borrowed successfully"));
   }

    @Test
    public void testReturnBook_Success() {
        TransactionRequest returnRequest = new TransactionRequest("Chicka Chicka Boom Boom", "RamuP");

        given()
                .spec(requestSpec)
                .body(returnRequest)
                .when()
                .post("/transactions/return")
                .then()
                .statusCode(200)
                .body("message", equalTo("Book returned successfully"));
    }

    @Test
    public void testBorrowingHistory_Success() {
        TransactionRequest historyRequest = new TransactionRequest("RamuP");

        Response response = given()
                .spec(requestSpec)
                .body(historyRequest)
                .when()
                .post("/transactions/borrowing-history")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Just an example assertion on response content
        assertTrue(response.asString().contains("Chicka Chicka Boom Boom"));
    }

    @Test
    public void testBorrowBook_BookNotAvailable() {
        TransactionRequest borrowRequest = new TransactionRequest("Notccsd", "RamuP");

        given()
                .spec(requestSpec)
                .body(borrowRequest)
                .when()
                .post("/transactions/borrow")
                .then()
                .statusCode(400)
                .body("message", equalTo("Book not available"));
    }

    @Test
    public void testBorrowBook_UserNotFound() {
        TransactionRequest borrowRequest = new TransactionRequest("DWP","ROCKP");

        given()
                .spec(requestSpec)
                .body(borrowRequest)
                .when()
                .post("/transactions/borrow")
                .then()
                .statusCode(404)
                .body("message", equalTo("User does not exist"));
    }

    @Test
    public void testReturnBook_NoActiveBorrowingOrUserNotFound() {
        TransactionRequest returnRequest = new TransactionRequest("Some Book", "UnknownUser");

        given()
                .spec(requestSpec)
                .body(returnRequest)
                .when()
                .post("/transactions/return")
                .then()
                .statusCode(404)
                .body("message", equalTo("No active borrowing found or User does not exist"));
    }

    @Test
    public void testBorrowingHistory_UserNotFound() {
        TransactionRequest historyRequest = new TransactionRequest("ROCKP");

        given()
                .spec(requestSpec)
                .body(historyRequest)
                .when()
                .post("/transactions/borrowing-history")
                .then()
                .statusCode(404)
                .body("message", equalTo("User does not exist"));
    }

}
