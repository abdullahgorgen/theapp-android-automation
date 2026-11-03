package com.vrgsl.theapp.pages.home;

import com.vrgsl.theapp.pages.BasePage;
import com.vrgsl.theapp.pages.login.LoginPage;

public class HomePage extends BasePage {

    public LoginPage openLoginScreen() {
        click("loginScreenItem");
        return new LoginPage();
    }
}
