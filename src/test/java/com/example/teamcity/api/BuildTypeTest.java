package com.example.teamcity.api;


import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

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
        // (API) SuperUser creates a Project for a User to be assigned to as Project Admin
        superUserCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        // (Test data) Prepare a user with Project Admin role for the project created by SuperUser
        var userWithProjectAdminRole = testData.getUser();
        testData.getUser().getRoles().setRole(Collections.singletonList(
                new Role("PROJECT_ADMIN", "p:%s".formatted(testData.getProject().getId()))));
        // (API) SuperUser creates a user with Project Admin role
        superUserCheckedRequests.getRequest(USERS).create(userWithProjectAdminRole);

        // "Logging in" as userWithProjectAdminRole
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(userWithProjectAdminRole));

        // (API) userWithProjectAdminRole creates a build type for the project created by SuperUser
        userCheckedRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        // Check build type was created successfully
        var createdBuildType = userCheckedRequests.<BuildType>getRequest(BUILD_TYPES)
                .read(testData.getBuildType().getId());

        softly.assertThat(testData.getBuildType().getName())
                .as("Build type name is not correct")
                .isEqualTo(createdBuildType.getName());
    }

    @Test
    @DisplayName("Project admin should not be able to create a build type for not their project")
    @Tags({@Tag("Negative"), @Tag("Roles")})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        // (API) SuperUser creates a Project for a User to be assigned to as Project Admin
        superUserCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        // (Test data) Prepare a user with Project Admin role for the project created by SuperUser
        var userWithProjectAdminRole = testData.getUser();
        userWithProjectAdminRole.getRoles().setRole(Collections.singletonList(
                new Role("PROJECT_ADMIN", "p:%s".formatted(testData.getProject().getId()))));
        // (API) SuperUser creates a user with Project Admin role
        superUserCheckedRequests.getRequest(USERS).create(userWithProjectAdminRole);

        // (Test data) Prepare a new project
        Project project = generate(Project.class);
        // (API) SuperUser creates a new project
        superUserCheckedRequests.getRequest(PROJECTS).create(project);

        // (Test data) Set project1 as a project for which a build type will be created
        testData.getBuildType().setProject(project);

        // (Test data) Prepare a build type for project1
        var buildTypeForProject = generate(Collections.singletonList(project), BuildType.class);

        // Check buildTypeForProject1 was not created due to insufficient permissions
        new UncheckedRequest(Specifications.authSpec(userWithProjectAdminRole), BUILD_TYPES)
                .create(buildTypeForProject)
                .then().spec(ValidationResponseSpecifications.checkUserNotHavePermissionsToEditProject(project));
    }
}
