package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Tag("Regression")
public class CreateProjectTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/lawb4/teamcity-testing-framework";

    @Test
    @DisplayName("User should be able to create a project")
    @Tag("Positive")
    public void userCreatesProject() {
        // Prepare test environment and data
        loginAs(testData.getUser());

        // Interaction with UI
        CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // Check API state (correct state of sent data from UI to API level)
        var createdProject = superUserCheckedRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softly.assertThat(createdProject).isNotNull();

        // Check UI state (correct data processing and display on UI level)
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));
    }

    @Test
    @DisplayName("User should not be able to create a project without name")
    @Tag("Negative")
    public void userCreatesProjectWithoutName() {
        // Prepare test environment and data
        step("Login as user");
        step("Check number of projects");

        // Interaction with UI
        step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL)");
        step("Click Proceed");
        step("Set Project Name");
        step("Click Proceed");

        // Check API state (correct state of sent data from UI to API level)
        step("Check that number of projects did not change");

        // Check UI state (correct data processing and display on UI level)
        step("Check that following error appears: 'Project name must not be empty'");
    }
}