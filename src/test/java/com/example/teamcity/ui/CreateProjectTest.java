package com.example.teamcity.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Tag("Regression")
public class CreateProjectTest extends BaseUiTest {
    @Test
    @DisplayName("User should be able to create a project")
    @Tag("Positive")
    public void userCreatesProject() {
        // Prepare test environment and data
        step("Login as user");

        // Interaction with UI
        step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL)");
        step("Click Proceed");
        step("Fix Project Name and Build Type name values");
        step("Click Proceed");

        // Check API state (correct state of sent data from UI to API level)
        step("Check that all entities (project, build type) were successfully created with correct data on API level");

        // Check UI state (correct data processing and display on UI level)
        step("Check that project is visible on Projects Page (http://localhost:8111/favorite/projects)");
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