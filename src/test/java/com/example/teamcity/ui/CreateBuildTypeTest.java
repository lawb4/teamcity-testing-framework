package com.example.teamcity.ui;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static io.qameta.allure.Allure.step;

@Tag("Regression")
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String TEST_REPO_URL_1 = "https://github.com/lawb4/teamcity-testing-framework";
    private static final String TEST_REPO_URL_2 = "https://github.com/lawb4/qa-portfolio";

    @BeforeEach
    public void setup() {
        loginAs(testData.getUser());
        createProject(testData.getProject());
    }

    @Test
    @DisplayName("User should be able to create a build configuration")
    @Tag("Positive")
    public void userSuccessfullyCreatesBuildType() {

        step("Create a buildType via UI", () -> {
            CreateBuildTypePage.open(testData.getProject().getId())
                    .createForm(TEST_REPO_URL_1)
                    .setupBuildType(testData.getBuildType().getName());
        });

        step("API check that buildType was successfully created", () -> {
            var createdBuildType = superUserCheckedRequests.<BuildType>getRequest(BUILD_TYPES)
                    .read("name:" + testData.getBuildType().getName());
            softly.assertThat(testData.getBuildType().getName()).isEqualTo(createdBuildType.getName());

            TestDataStorage.getStorage().addCreatedEntity(BUILD_TYPES, createdBuildType);
        });

        step("Check that buildType is displayed on Project page", () -> {
            var foundBuildTypes = ProjectPage.open(testData.getProject().getId())
                    .getBuildTypes().stream()
                    .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
            softly.assertThat(foundBuildTypes).isTrue();
        });
    }

    @Test
    @DisplayName("User should not be able to create a build configuration with empty name")
    @Tag("Negative")
    public void userCreatesBuildConfigurationWithEmptyName() {

        step("Create a buildType without name via UI", () -> {
            CreateBuildTypePage.open(testData.getProject().getId())
                    .createForm(TEST_REPO_URL_1)
                    .setupBuildType("")
                    .shouldShowEmptyBuildTypeNameError();
        });

        step("API check that buildType was not created", () -> {
            var response = superUserUncheckedRequests.getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
        });
    }

    @Test
    @DisplayName("User should not be able to create a build configuration with name that already exists for another build configuration")
    @Tag("Negative")
    public void userCreatesBuildTypeWithAlreadyExistingBuildTypeName() {

        step("Create a buildType via UI", () -> {
            CreateBuildTypePage.open(testData.getProject().getId())
                    .createForm(TEST_REPO_URL_1)
                    .setupBuildType(testData.getBuildType().getName());
        });

        step("Create a buildType with already existing name via UI", () -> {
            CreateBuildTypePage.open(testData.getProject().getId())
                    .createForm(TEST_REPO_URL_2)
                    .setupBuildType(testData.getBuildType().getName())
                    .shouldShowDuplicateNameError(testData.getBuildType().getName(), testData.getProject().getName());
        });

        step("API check that buildType was not created", () -> {
            var response = superUserUncheckedRequests.getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
        });
    }
}
