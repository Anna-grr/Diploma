package ru.netology.test.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class CreditTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterEach
    public void teardrop() {
        cleanDatabase();
    }

    @Test
    void happyPath() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("APPROVED");
    }

    @Test
    void sadPath() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidDeclinedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("DECLINED");
    }

    @Test
    void shouldSucceedIfCurrentMonthAndCurrentYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithCurrentMonthAndCurrentYear();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs4YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus4YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfExpirationDateIs5YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithPlus5YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("APPROVED");
    }

    @Test
    void shouldSucceedIfHyphenatedHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardWithHyphenatedName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getSuccessNotification();
        SQLHelper.assertCreditStatus("APPROVED");
    }

    @Test
    void shouldGetErrorNotifyIfEmptyNumber() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "number");
        formPage.checkErrorNotifyIfEmptyNumber();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains15Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith15Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains17Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith17Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains16RandomDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith16RandomDigits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotification();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithLettersInNumber();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidNumber();
    }


    @Test
    void shouldGetErrorNotifyIfEmptyMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "monthField");
        formPage.checkErrorNotifyIfEmptyMonth();
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs00() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith00Month();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidMonth();
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs13() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith13thMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidMonth();
    }

    @Test
    void shouldGetErrorNotifyIfExpiredMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfExpiredMonth();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "yearField");
        formPage.checkErrorNotifyIfEmptyYear();
    }

    @Test
    void shouldGetErrorNotifyIfExpirationDateIs6YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardWithPlus6YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidYear();
    }

    @Test
    void shouldGetErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredYear();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfExpiredYear();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "holderField");
        formPage.checkErrorNotifyIfEmptyHolder();
    }

    @Test
    void shouldGetErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithCyrillicName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithDigitsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithSpecialSymbolsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "cvcField");
        formPage.checkErrorNotifyIfEmptyCVC();
    }

    @Test
    void shouldGetErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWith2Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }

    @Test
    void shouldGetErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWithLetters();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.checkErrorNotifyIfInvalidCVC();
    }
}
