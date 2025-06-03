package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";

    private final SelenideElement projectInputName = $("#projectName");

    public static CreateProjectPage open() {
        return Selenide.open(CREATE_URL.formatted("_Root", PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    public CreateProjectPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectInputName.val(projectName);
        inputBuildTypeName.val(buildTypeName);
        submitButton.click();
        Selenide.sleep(1000);
    }
}
