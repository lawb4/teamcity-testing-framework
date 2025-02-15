package com.example.teamcity.api;


import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
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
    @DisplayName("User should be able to create build type")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesBuildTypeTest() {
        var user = generate(User.class);

        superUserCheckedRequests.getRequest(USERS).create(user);
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(user));

        var project = generate(Project.class);

        project = userCheckedRequests.<Project>getRequest(PROJECTS).create(project);

        var buildType = generate(Collections.singletonList(project), BuildType.class);

        userCheckedRequests.getRequest(BUILD_TYPES).create(buildType);

        var createdBuildType = userCheckedRequests.<BuildType>getRequest(BUILD_TYPES).read(buildType.getId());

        softly.assertThat(buildType.getName())
                .as("Build type name is not correct")
                .isEqualTo(createdBuildType.getName());
    }

    @Test
    @DisplayName("User should not be able to create two build types with the same id")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var user = generate(User.class);

        superUserCheckedRequests.getRequest(USERS).create(user);
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(user));

        var project = generate(Project.class);
        project = userCheckedRequests.<Project>getRequest(PROJECTS).create(project);

        var buildType1 = generate(Collections.singletonList(project), BuildType.class);
        var buildType2 = generate(Collections.singletonList(project), BuildType.class,
                buildType1.getId());

        userCheckedRequests.getRequest(BUILD_TYPES).create(buildType1);
        new UncheckedRequest(Specifications.authSpec(user), BUILD_TYPES)
                .create(buildType2)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(buildType1.getId())));
    }

    @Test
    @DisplayName("Project admin should be able to create build type for their project")
    @Tags({@Tag("Positive"), @Tag("Roles")})
    public void projectAdminCreatesBuildTypeTest() {
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user (PROJECT_ADMIN)");
        step("Check buildType was created successfully");
    }

    @Test
    @DisplayName("Project admin should not be able to create build type for not their project")
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
