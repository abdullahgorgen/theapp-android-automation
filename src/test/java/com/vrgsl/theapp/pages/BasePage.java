package com.vrgsl.theapp.pages;

import com.vrgsl.theapp.driver.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.vrgsl.theapp.helper.ElementHelper.getBy;

public abstract class BasePage {

    private static final Duration EXPLICIT_WAIT = Duration.ofSeconds(10);

    protected final AndroidDriver driver = DriverManager.getDriver();
    protected final WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_WAIT);

    protected WebElement waitForVisible(String key) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(key)));
    }

    protected void click(String key) {
        wait.until(ExpectedConditions.elementToBeClickable(getBy(key))).click();
    }

    protected void type(String key, String text) {
        waitForVisible(key).sendKeys(text);
    }

    protected String getText(String key) {
        return waitForVisible(key).getText();
    }

    // Returns false instead of throwing, so the caller can assert on the result
    protected boolean isVisible(String key) {
        try {
            waitForVisible(key);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
