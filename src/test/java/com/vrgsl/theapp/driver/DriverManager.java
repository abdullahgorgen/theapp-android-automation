package com.vrgsl.theapp.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DriverManager {

    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    private static final String DEVICE_UDID = "emulator-5554";
    private static final Path APP_PATH = Path.of("apps", "TheApp.apk").toAbsolutePath();
    private static final String APP_PACKAGE = "com.appiumpro.the_app";
    private static final String APP_ACTIVITY = "com.appiumpro.the_app.MainActivity";

    private static AndroidDriver driver;

    private DriverManager() {
    }

    public static AndroidDriver getDriver() {
        return driver;
    }

    public static void createDriver() {
        if (!Files.isRegularFile(APP_PATH)) {
            throw new IllegalStateException("APK not found: " + APP_PATH);
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(DEVICE_UDID)
                .setApp(APP_PATH.toString())
                .setAppPackage(APP_PACKAGE)
                .setAppActivity(APP_ACTIVITY)
                // The app remembers the logged-in user across restarts, so its data is cleared for every session
                .setNoReset(false);

        driver = new AndroidDriver(appiumServerUrl(), options);
    }

    public static void quitDriver() {
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } finally {
            driver = null;
        }
    }

    private static URL appiumServerUrl() {
        try {
            return URI.create(APPIUM_SERVER_URL).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL: " + APPIUM_SERVER_URL, e);
        }
    }
}
