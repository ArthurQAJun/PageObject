package ru.netology.Test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.DataHelper.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.DataHelper.DataHelper.*;

public class MoneyTransferTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;


    @BeforeEach
    void setup(){

        loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void ShouldTestFirstToSecond() {

            var firstCardInfo = getFirstCardInfo();
            var secondCardInfo = getSecondCardInfo();
            var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
            var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
            var amount = generateValidAmount(firstCardBalance);
            var expectedBalanceFirstCard = firstCardBalance - amount;
            var expectedBalanceSecondCard = secondCardBalance + amount;
            var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
            dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
            var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
            var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
            assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
            assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
}


