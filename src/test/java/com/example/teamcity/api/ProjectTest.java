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
    @DisplayName("User should be able to find a project by its name")
    @Tags({@Tag("Positive"), @Tag("Search")})
    public void userFindsProjectByItsName() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        userCheckedRequests.getRequest(PROJECTS).search("name:" + testData.getProject().getName());

        softly.assertThat(testData.getProject().getName())
                .as("Project was not found")
                .isEqualTo(createdProject.getName());
    }
}