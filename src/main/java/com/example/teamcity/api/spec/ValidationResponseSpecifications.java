package com.example.teamcity.api.spec;

import com.example.teamcity.api.models.Project;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {

    public static ResponseSpecification checkProjectWithNameAlreadyExists(String projectName) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.containsString("Project with this name already exists: %s".formatted(projectName)))
                .build();
    }

    public static ResponseSpecification checkProjectNameCannotBeEmpty() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.containsString("Project name cannot be empty"))
                .build();
    }

    public static ResponseSpecification checkProjectIdMustNotBeEmpty() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID must not be empty"))
                .build();
    }

    public static ResponseSpecification checkProjectIdIsUsedByAnotherProject(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.containsString("Project ID \"%s\" is already used by another project".formatted(project.getId())))
                .build();
    }

    public static ResponseSpecification checkProjectIdCannotStartWithDigit(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID \"%s\" is invalid: starts with non-letter character '%c'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), project.getId().charAt(0))))
                .build();
    }

    public static ResponseSpecification checkProjectIdLengthCannotExceedMaximumValue(Project project, int projectIdLength) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID \"%s\" is invalid: it is %d characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), projectIdLength)))
                .build();
    }

    public static ResponseSpecification checkProjectIdCannotContainCyrillicCharacters(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID \"%s\" is invalid: contains non-latin letter '%c'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), project.getId().charAt(5))))
                .build();
    }

    public static ResponseSpecification checkProjectIdCannotStartWithSpecialCharacters(Project project, char specialCharacter) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID \"%s\" is invalid: starts with non-letter character '%c'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), specialCharacter)))
                .build();
    }

    public static ResponseSpecification checkProjectIdCannotContainSpecialCharactersExceptUnderscore(Project project, char specialCharacter) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .expectBody(Matchers.containsString("Project ID \"%s\" is invalid: contains unsupported character '%c'. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)".formatted(project.getId(), specialCharacter)))
                .build();
    }

    public static ResponseSpecification checkUserNotHaveCreateSubprojectPermissionForProject(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.containsString("You do not have \"Create subproject\" permission in project with internal id: %s".formatted(project.getParentProject().getId())))
                .build();
    }
  
    public static ResponseSpecification checkUserNotHavePermissionsToEditProject(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.containsString("You do not have enough permissions to edit project with id: %s".formatted(project.getId())))
                .build();
    }
}
