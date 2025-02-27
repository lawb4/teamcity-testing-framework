package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static com.example.teamcity.api.models.Role.*;

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

    @DisplayName("User should be able to create a project")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(createdProject)
                .as("Project structure is not correct")
                .isEqualTo(testData.getProject());
    }

    @Test
    @DisplayName("User should be able to create a project with a maximum Project Id length (225 characters)")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithMaxProjectIdLengthTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        testData.getProject().setId(RandomData.getString(225));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getId())
                .as("Project id length is not valid")
                .isEqualTo(createdProject.getId());
    }

    @Test
    @DisplayName("User should be able to create a project with a name containing more than 225 characters")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithNameContainingAlotOfSymbolsTest() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        testData.getProject().setName(RandomData.getString(
                RandomData.getRandomNumberExceedingLimits()));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getId())
                .as("Project id length is not valid")
                .isEqualTo(createdProject.getId());
    }

    @Test
    @DisplayName("User should be able to create a project with a Project Id containing latin letters and digits")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithProjectIdContainingLatinLettersAndDigits() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        testData.getProject().setId(RandomData.getStringWithLatinLettersAndDigits());

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getId())
                .as("Project id is not valid")
                .isEqualTo(createdProject.getId());
    }

    @Test
    @DisplayName("User should be able to create a project with a Project Id containing repeating symbols")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithProjectIdContainingRepeatingSymbols() {
        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        testData.getProject().setId(RandomData.getStringWithRepeatingSymbols());

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(testData.getProject().getId())
                .as("Project id is not valid")
                .isEqualTo(createdProject.getId());
    }

    @Test
    @DisplayName("User should be able to create a project with cyrillic characters in its name")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithCyrillicCharactersNameTest() {
        var project = testData.getProject();
        project.setName(RandomData.getStringWithCyrillic());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(project);

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(project.getId());

        softly.assertThat(project.getName())
                .as("Project name is not correct")
                .isEqualTo(createdProject.getName());
    }

    @Test
    @DisplayName("User should be able to create a project with Project ID ending with 'underscore' special character")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithProjectIdEndingWithUnderscoreTest() {
        var project = testData.getProject();
        project.setId(RandomData.getStringEndsWithSpecialCharacter('_'));

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(project);

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(project.getId());

        softly.assertThat(project.getName())
                .as("Project id is not correct")
                .isEqualTo(createdProject.getName());
    }

    @Test
    @DisplayName("User should be able to create a project with a Parent Project not equal '_Root' project")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithParentProjectIdNotEqualToRootProjectTest() {
        var project1 = testData.getProject();
        var project2 = generate().getProject();
        project2.setParentProject(project1);

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(project1);
        var createdProject2 = userCheckedRequests.<Project>getRequest(PROJECTS).create(project2);

        softly.assertThat(createdProject2.getParentProject().getId())
                .as("parentProjectId = _Root")
                .isEqualTo(project1.getId());
    }

    @Test
    @DisplayName("User should be able to create a project with a 'copyAllAssociatedSettings' setting = false")
    @Tags({@Tag("Positive"), @Tag("CRUD")})
    public void userSuccessfullyCreatesProjectWithCopyAllAssociatedSettingsFalseTest() {
        testData.getProject().setCopyAllAssociatedSettings(false);

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS)
                .read(testData.getProject().getId());

        softly.assertThat(createdProject.isCopyAllAssociatedSettings())
                .as("copyAllAssociatedSettings = true")
                .isEqualTo(testData.getProject().isCopyAllAssociatedSettings());
    }

    @Test
    @DisplayName("User should not be able to create two projects with the same name")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoProjectsWithSameNameTest() {
        var project1 = testData.getProject();
        var project2 = generate().getProject();
        project2.setName(project1.getName());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(project1);
        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project2)
                .then().spec(ValidationResponseSpecifications
                        .checkProjectWithNameAlreadyExists(testData.getProject().getName()));
    }

    @Test
    @DisplayName("User should not be able to create two projects with the same Project ID")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesTwoProjectsWithSameProjectIdTest() {
        var project1 = testData.getProject();
        var project2 = generate().getProject();
        project2.setId(project1.getId());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequests.getRequest(PROJECTS).create(project1);
        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project2)
                .then().spec(ValidationResponseSpecifications
                        .checkProjectIdIsUsedByAnotherProject(project1));
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
                .then().spec(ValidationResponseSpecifications.checkProjectNameCannotBeEmpty());
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
                .then().spec(ValidationResponseSpecifications.checkProjectIdMustNotBeEmpty());
    }

    @Test
    @DisplayName("User should not be able to create a project with an empty Name and empty Project ID")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithEmptyNameAndEmptyProjectId() {
        var project = generate(Project.class);
        project.setId("");
        project.setName("");

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectNameCannotBeEmpty());
    }

    @Test
    @DisplayName("User should not be able to create a project with an empty Name and invalid Project ID")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithEmptyNameAndInvalidProjectId() {
        var project = generate(Project.class);
        project.setId(RandomData.getStringStartingWithDigit());
        project.setName("");

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectNameCannotBeEmpty());
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
                .then().spec(ValidationResponseSpecifications.checkProjectIdCannotStartWithDigit(project));
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
                .then().spec(ValidationResponseSpecifications.checkProjectIdLengthCannotExceedMaximumValue(project, projectIdLength));
    }

    @Test
    @DisplayName("User should not be able to create a project with cyrillic characters in its id")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectWithCyrillicCharactersInProjectIdTest() {
        var project = testData.getProject();
        project.setId(RandomData.getStringWithCyrillic());

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectIdCannotContainCyrillicCharacters(project));
    }

    @ParameterizedTest
    @MethodSource("com.example.teamcity.api.generators.RandomData#invalidCharacters")
    @DisplayName("User should not be able to create a project with Project ID starting with special characters")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectStartingWithSpecialCharactersTest(char specialCharacter) {
        var project = testData.getProject();
        project.setId(RandomData.getStringStartsWithSpecialCharacter(specialCharacter));

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectIdCannotStartWithSpecialCharacters(project, specialCharacter));
    }

    @ParameterizedTest
    @MethodSource("com.example.teamcity.api.generators.RandomData#invalidCharactersExceptUnderscore")
    @DisplayName("User should not be able to create a project with Project ID containing special characters (except underscore)")
    @Tags({@Tag("Negative"), @Tag("CRUD")})
    public void userCreatesProjectContainsSpecialCharactersExceptUnderscoreTest(char specialCharacter) {
        var project = testData.getProject();
        project.setId(RandomData.getStringEndsWithSpecialCharacter(specialCharacter));

        superUserCheckedRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedRequest(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectIdCannotContainSpecialCharactersExceptUnderscore(project, specialCharacter));
    }

    @Test
    @DisplayName("Project Viewer should not be able to create a project")
    @Tags({@Tag("Negative"), @Tag("Roles")})
    public void projectViewerCreatesProjectTest() {
        // (API) SuperUser creates a Project for a User to be assigned to as Project Viewer
        superUserCheckedRequests.getRequest(PROJECTS).create(testData.getProject());
        // (Test data) Prepare a new project to be created by a Project Viewer
        var project = generate().getProject();

        var userWithProjectViewerRole = testData.getUser();
        userWithProjectViewerRole.getRoles().setRole(Collections.singletonList(
                new Role(PROJECT_VIEWER, "p:%s".formatted(testData.getProject().getId()))));
        superUserCheckedRequests.getRequest(USERS).create(userWithProjectViewerRole);

        new UncheckedRequest(Specifications.authSpec(userWithProjectViewerRole), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkUserNotHaveCreateSubprojectPermissionForProject(project));
    }

    @Test
    @DisplayName("Project Developer should not be able to create a project")
    @Tags({@Tag("Negative"), @Tag("Roles")})
    public void projectDeveloperCreatesProjectTest() {
        // (API) SuperUser creates a Project for a User to be assigned to as Project Developer
        superUserCheckedRequests.getRequest(PROJECTS).create(testData.getProject());
        // (Test data) Prepare a new project to be created by a Project Developer
        var project = generate().getProject();

        var userWithProjectDeveloperRole = testData.getUser();
        userWithProjectDeveloperRole.getRoles().setRole(Collections.singletonList(
                new Role(PROJECT_DEVELOPER, "p:%s".formatted(testData.getProject().getId()))));
        superUserCheckedRequests.getRequest(USERS).create(userWithProjectDeveloperRole);

        new UncheckedRequest(Specifications.authSpec(userWithProjectDeveloperRole), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkUserNotHaveCreateSubprojectPermissionForProject(project));
    }

    @Test
    @DisplayName("Agent Manager should not be able to create a project")
    @Tags({@Tag("Negative"), @Tag("Roles")})
    public void agentManagerCreatesProjectTest() {
        // (API) SuperUser creates a Project for a User to be assigned to as Agent Manager
        superUserCheckedRequests.getRequest(PROJECTS).create(testData.getProject());
        // (Test data) Prepare a new project to be created by a Agent Manager
        var project = generate().getProject();

        var userWithAgentManagerRole = testData.getUser();
        userWithAgentManagerRole.getRoles().setRole(Collections.singletonList(
                new Role(AGENT_MANAGER, "p:%s".formatted(testData.getProject().getId()))));
        superUserCheckedRequests.getRequest(USERS).create(userWithAgentManagerRole);

        new UncheckedRequest(Specifications.authSpec(userWithAgentManagerRole), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkUserNotHaveCreateSubprojectPermissionForProject(project));
    }
}