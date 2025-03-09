package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildTypePage extends CreateBasePage {
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    public SelenideElement inputBuildTypeName = $("#buildTypeName");
    private final SelenideElement inputBuildTypeName = $("#buildTypeName");
    private final SelenideElement errorBuildTypeName = $("#error_buildTypeName");

    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);
    }

    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public CreateBuildTypePage setupBuildType(String buildTypeName) {
        inputBuildTypeName.val(buildTypeName);
        submitButton.click();
        return this;
    }

    public void shouldShowEmptyBuildTypeNameError() {
        errorBuildTypeName.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Build configuration name must not be empty"));
    }

    public void shouldShowDuplicateNameError(String buildTypeName, String projectName) {
        errorBuildTypeName.shouldBe(Condition.visible)
                .shouldHave(Condition.text(
                        "Build configuration with name \"%s\" already exists in project: \"%s\"".formatted(buildTypeName, projectName)));
    }
}
