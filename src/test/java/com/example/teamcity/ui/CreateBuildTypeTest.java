package com.example.teamcity.ui;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Tag("Regression")
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String REPO_URL_FOR_PROJECT = "https://github.com/lawb4/qa-portfolio";
    private static final String REPO_URL_FOR_BUILD_TYPE = "https://github.com/lawb4/teamcity-testing-framework";

    @Test
    @DisplayName("User should be able to create a build configuration")
    @Tag("Positive")
    public void userSuccessfullyCreatesBuildType() {
        loginAs(testData.getUser());

        CreateProjectPage.open()
                .createForm(REPO_URL_FOR_PROJECT)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        var createdProject = superUserCheckedRequests.<Project>getRequest(PROJECTS)
                .read("name:" + testData.getProject().getName());

        var newBuildTypeTestData = generate().getBuildType();

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL_FOR_BUILD_TYPE)
                .setupBuildType(newBuildTypeTestData.getName());

        var createdBuildType = superUserCheckedRequests.<BuildType>getRequest(BUILD_TYPES)
                .read("name:" + newBuildTypeTestData.getName());
        softly.assertThat(createdBuildType).isNotNull();

        TestDataStorage.getStorage().addCreatedEntity(BUILD_TYPES, createdBuildType);

        var foundBuildTypes = ProjectPage.open(createdProject.getId())
                .getBuildTypes().stream()
                .anyMatch(buildType
                        -> buildType.getName().text().equals(newBuildTypeTestData.getName()));
        softly.assertThat(foundBuildTypes).isTrue();
    }

    @Test
    @DisplayName("User should not be able to create a build configuration with empty name")
    @Tag("Negative")
    public void userCreatesBuildConfigurationWithEmptyName() {
        loginAs(testData.getUser());

        CreateProjectPage.open()
                .createForm(REPO_URL_FOR_PROJECT)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        var createdProject = superUserCheckedRequests.<Project>getRequest(PROJECTS).read("name:" + testData.getProject().getName());

        TestDataStorage.getStorage().addCreatedEntity(PROJECTS, createdProject);

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL_FOR_BUILD_TYPE)
                .setupBuildType("")
                .shouldShowEmptyBuildTypeNameError();
    }

    @Test
    @DisplayName("User should not be able to create a build configuration with name that already exists for another build configuration")
    @Tag("Negative")
    public void userCreatesBuildTypeWithAlreadyExistingBuildTypeName() {
        loginAs(testData.getUser());

        CreateProjectPage.open()
                .createForm(REPO_URL_FOR_PROJECT)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        var createdProject = superUserCheckedRequests.<Project>getRequest(PROJECTS).read("name:" + testData.getProject().getName());

        TestDataStorage.getStorage().addCreatedEntity(PROJECTS, createdProject);

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL_FOR_BUILD_TYPE)
                .setupBuildType(testData.getBuildType().getName())
                .shouldShowDuplicateNameError(testData.getBuildType().getName(), createdProject.getName());
    }
}
