package ru.netology.test.api;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.gson.Gson;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.APIHelper;

import static org.hamcrest.Matchers.equalTo;


public class CreditAPITest {

    private static DataHelper.CardInfo cardInfo;
    private static final Gson gson = new Gson();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void shouldRespondWithStatus200IfApprovedCard() {
        cardInfo = DataHelper.getValidApprovedCard();
        var body = gson.toJson(cardInfo);
        APIHelper.makeCreditRequest(body, 200)
                .body("status", equalTo("APPROVED"));
    }

    @Test
    void shouldRespondWithStatus200IfDeclinedCard() {
        cardInfo = DataHelper.getValidDeclinedCard();
        var body = gson.toJson(cardInfo);
        APIHelper.makeCreditRequest(body, 200)
                .body("status", equalTo("DECLINED"));
    }

    @Test
    void shouldRespondWithStatus400IfEmptyBody() {
        APIHelper.makeCreditRequest("", 400);
    }
}

