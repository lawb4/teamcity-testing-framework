package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

@Tag("Regression")
public class ProjectTest extends BaseApiTest {

    @Test
    @DisplayName("User should be able to create a project")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesProjectTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getName())
                .as("Project name is not correct")
                .isEqualTo(createdProject.getName());
    }
}