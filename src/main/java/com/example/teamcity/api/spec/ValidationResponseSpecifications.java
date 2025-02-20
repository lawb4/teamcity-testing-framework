package com.example.teamcity.api.spec;

import com.example.teamcity.api.models.Project;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {
    public static ResponseSpecification checkProjectIdCannotStartWithDigit(Project project) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.containsString("You do not have enough permissions to edit project with id: %s".formatted(project.getId())))
                .build();
    }
}