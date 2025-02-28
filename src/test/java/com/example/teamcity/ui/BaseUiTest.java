package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

public class BaseUiTest extends BaseTest {

    @BeforeEach
    public void setupUiTest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");
        //Configuration.timeout = 8000;

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true,
                        "enableLog", true)
        );
    }

    @AfterEach
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }
}
