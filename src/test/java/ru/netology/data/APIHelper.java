package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class APIHelper {

    private static final Gson gson = new Gson();

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static ValidatableResponse makePaymentRequest(String body, int statusCode) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/payment")
                .then()
                .statusCode(statusCode);
    }

    public static ValidatableResponse makeCreditRequest(String body, int statusCode) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/payment")
                .then()
                .statusCode(statusCode);
    }

}
