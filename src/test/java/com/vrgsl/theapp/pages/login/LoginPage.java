package com.vrgsl.theapp.pages.login;

import com.vrgsl.theapp.pages.BasePage;

public class LoginPage extends BasePage {

    public void enterUsername(String username) {
        type("usernameField", username);
    }

    public void enterPassword(String password) {
        type("passwordField", password);
    }

    // The next screen depends on the credentials, so the caller decides which page to continue with
    public void tapLogin() {
        click("loginButton");
    }

    // The error is a native Android dialog that the alert API cannot see, so it is read as a regular element
    public String getErrorMessage() {
        return getText("loginErrorMessage");
    }
}
