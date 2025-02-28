package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement inputUrl = $("#url");
    protected SelenideElement inputBuildTypeName = $("#buildTypeName");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));

    protected void baseCreateForm(String url) {
        inputUrl.val(url);
        submitButton.click();
    }
}
