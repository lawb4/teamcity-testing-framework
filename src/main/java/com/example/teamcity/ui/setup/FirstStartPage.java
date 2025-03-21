package com.example.teamcity.ui.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class FirstStartPage extends BasePage {
    private final SelenideElement proceedButton = $("#proceedButton");
    private final SelenideElement restoreButton = $("#restoreButton");
    private final SelenideElement dbTypeSelector = $("#dbType");
    private final SelenideElement acceptLicenseCheckbox = $("#accept");
    private final SelenideElement submitButton = $("input[type='submit']");

    public FirstStartPage() {
        restoreButton.shouldBe(Condition.visible, LONG_WAITING);
    }

    public static FirstStartPage open() {
        return Selenide.open("/", FirstStartPage.class);
    }

    public FirstStartPage setupFirstStart() {
        proceedButton.click();
        dbTypeSelector.shouldBe(Condition.visible, LONG_WAITING);
        proceedButton.click();
        acceptLicenseCheckbox.shouldBe(Condition.exist, LONG_WAITING).scrollTo().click();
        submitButton.click();
        return this;
    }
}
