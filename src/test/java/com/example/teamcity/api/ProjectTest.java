package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
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

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Tag("Regression")
public class ProjectTest extends BaseApiTest {
    @Test
    @DisplayName("User should be able to create a project")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesProjectTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getName())
                .as("Project name is not correct")
                .isEqualTo(createdProject.getName());
    }

    @Test
    @DisplayName("User should be able to create a project with a maximum Project Id length (225 characters)")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userCreatesProjectWithMaxProjectIdLengthTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        testData.getProject().setId(RandomData.getString(225));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getId())
                .as("Project id is not valid")
                .isEqualTo(createdProject.getId());
    }

    @Test
    @DisplayName("User should not be able to create two projects with the same name")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoProjectsWithSameNameTest() {
        var project = testData.getProject();

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());
        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: %s"
                        .formatted(testData.getProject().getName())));
    }

    @Test
    @DisplayName("User should not be able to create a project with an empty name")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithEmptyName() {
        var project = generate(Project.class);
        project.setName("");

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));
    }

    @Test
    @DisplayName("User should not be able to create a project with an empty Project ID")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithEmptyProjectId() {
        var project = generate(Project.class);
        project.setId("");

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty"));
    }

    @Test
    @DisplayName("User should not be able to create a project with a Project ID that starts with a digit")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithProjectIdStartsWithDigit() {
        var project = testData.getProject();
        project.setId(RandomData.getStringStartingWithDigit());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"%s\" is invalid: starts with non-letter character '%c'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), project.getId().charAt(0))));
    }

    @Test
    @DisplayName("User should not be able to create a project with a Project Id length that exceed maximum value (>= 226 characters)")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithExceedingProjectIdLengthTest() {
        var project = testData.getProject();
        int projectIdLength = 226;
        project.setId(RandomData.getString(projectIdLength));

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"%s\" is invalid: it is %d characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), projectIdLength)));
    }
}