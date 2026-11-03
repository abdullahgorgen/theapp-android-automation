package com.vrgsl.theapp.pages.login;

import com.vrgsl.theapp.pages.BasePage;

public class SecretAreaPage extends BasePage {

    // The message has no id or content-desc, so its locator matches the start of the text
    public String getLoggedInMessage() {
        return getText("loggedInMessage");
    }

    public LoginPage tapLogout() {
        click("logoutButton");
        return new LoginPage();
    }
}
