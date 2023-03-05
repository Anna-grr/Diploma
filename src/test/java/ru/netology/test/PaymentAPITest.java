package ru.netology.test;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PaymentAPITest {
    private static DataHelper.CardInfo cardInfo;
    private static final Gson gson = new Gson();

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/payment")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    @Test
    void happyPath() {
        cardInfo = DataHelper.getValidApprovedCard();
        var body = gson.toJson(cardInfo);
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    @Test
    void sadPath() {
        cardInfo = DataHelper.getValidDeclinedCard();
        var body = gson.toJson(cardInfo);
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("status", equalTo("DECLINED"));
    }

    @Test
    void shouldRespondWithStatus400IfEmptyBody() {
        given()
                .spec(requestSpec)
                .body("")
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}
