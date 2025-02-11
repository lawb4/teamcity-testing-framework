package com.example.teamcity.api;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Tag("Regression")
public class BuildTypeTest extends BaseApiTest {
    @Test
    @DisplayName("User should be able to create build type")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesBuildConfigurationTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType for project by user");
        step("Check buildType was created successfully with correct data");
    }

    @Test
    @DisplayName("User should not be able to create two build types with the same id")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 with the same id as buildType1 for project by user");
        step("Check buildType2 was not created with bad request code");
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
