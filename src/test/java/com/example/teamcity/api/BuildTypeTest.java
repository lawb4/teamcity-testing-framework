package com.example.teamcity.api;


import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Tag("Regression")
public class BuildTypeTest extends BaseApiTest {
    @Test
    @DisplayName("User should be able to create a build type")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesBuildTypeTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckedRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckedRequests.<BuildType>getRequest(BUILD_TYPES)
                .read(testData.getBuildType().getId());

        softly.assertThat(testData.getBuildType().getName())
                .as("Build type name is not correct")
                .isEqualTo(createdBuildType.getName());
    }

    @Test
    @DisplayName("User should not be able to create two build types with the same id")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Collections.singletonList(testData.getProject()), BuildType.class,
                testData.getBuildType().getId());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckedRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedRequest(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test
    @DisplayName("Project admin should be able to create a build type for their project")
    @Tags({@Tag("Positive"), @Tag("Roles")})
    public void projectAdminCreatesBuildTypeTest() {
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user (PROJECT_ADMIN)");
        step("Check buildType was created successfully");
    }

    @Test
    @DisplayName("Project admin should not be able to create a build type for not their project")
    @Tags({@Tag("Negative"), @Tag("Roles")})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        step("Create user1");
        step("Create project1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2");
        step("Grant user2 PROJECT_ADMIN role in project2");

        step("Create buildType for project1 by user2");
        step("Check buildType was not created with forbidden code");
    }
}
