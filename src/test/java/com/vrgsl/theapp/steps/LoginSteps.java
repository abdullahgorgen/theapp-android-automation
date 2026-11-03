package com.vrgsl.theapp.steps;

import com.thoughtworks.gauge.Step;
import com.vrgsl.theapp.pages.home.HomePage;
import com.vrgsl.theapp.pages.login.LoginPage;
import com.vrgsl.theapp.pages.login.SecretAreaPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private LoginPage loginPage;

    // Gauge reuses this instance across scenarios, so the page is created per scenario with its new driver
    @Step("Open login screen")
    public void openLoginScreen() {
        loginPage = new HomePage().openLoginScreen();
    }

    @Step("Enter username <username>")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @Step("Enter password <password>")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Step("Tap login button")
    public void tapLogin() {
        loginPage.tapLogin();
    }

    @Step("Verify user is logged in as <username>")
    public void verifyLoggedInAs(String username) {
        assertThat(new SecretAreaPage().getLoggedInMessage())
                .as("logged in message")
                .isEqualTo("You are logged in as " + username);
    }

    @Step("Tap logout button")
    public void tapLogout() {
        loginPage = new SecretAreaPage().tapLogout();
    }

    @Step("Verify user is logged out")
    public void verifyLoggedOut() {
        assertThat(loginPage.isDisplayed())
                .as("login screen is displayed")
                .isTrue();
    }

    @Step("Verify error message <message> is displayed")
    public void verifyErrorMessage(String message) {
        assertThat(loginPage.getErrorMessage())
                .as("error message")
                .isEqualTo(message);
    }
}
