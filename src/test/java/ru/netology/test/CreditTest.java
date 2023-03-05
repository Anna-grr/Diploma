package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class CreditTest {

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
        formPage.getErrorNotifyIfEmptyNumber();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains15Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith15Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidNumber();
    }

    @Test
    void shouldGetErrorNotifyIfNumberContains17Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith17Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidNumber();
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
        formPage.getErrorNotifyIfInvalidNumber();
    }


    @Test
    void shouldGetErrorNotifyIfEmptyMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "monthField");
        formPage.getErrorNotifyIfEmptyMonth();
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs00() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith00Month();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidMonth();
    }

    @Test
    void shouldGetErrorNotifyIfMonthValueIs13() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWith13thMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidMonth();
    }

    @Test
    void shouldGetErrorNotifyIfExpiredMonth() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredMonth();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfExpiredMonth();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "yearField");
        formPage.getErrorNotifyIfEmptyYear();
    }

    @Test
    void shouldGetErrorNotifyIfExpirationDateIs6YearsFromCurrent() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardWithPlus6YearsFromCurrent();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidYear();
    }

    @Test
    void shouldGetErrorNotifyIfExpiredYear() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithExpiredYear();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfExpiredYear();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyHolder() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "holderField");
        formPage.getErrorNotifyIfEmptyHolder();
    }

    @Test
    void shouldGetErrorNotifyIfCyrillicHolderName() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithCyrillicName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorIfHolderNameContainsDigits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithDigitsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorIfHolderNameContainsSpecialSymbols() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardWithSpecialSymbolsInName();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidHolder();
    }

    @Test
    void shouldGetErrorNotifyIfEmptyCVC() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidApprovedCard();
        var formPage = mainPage.openCreditForm();
        formPage.setEmptyValue(cardInfo, "cvcField");
        formPage.getErrorNotifyIfEmptyCVC();
    }

    @Test
    void shouldGetErrorNotifyIfCVCContains2Digits() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWith2Digits();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidCVC();
    }

    @Test
    void shouldGetErrorNotifyIfCVCContainsLetters() {
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVCWithLetters();
        var formPage = mainPage.openCreditForm();
        formPage.setValues(cardInfo);
        formPage.getErrorNotifyIfInvalidCVC();
    }
}
