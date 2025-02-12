package com.example.teamcity.api;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class DummyTest extends BaseApiTest {
    @Test
    public void userShouldBeAbleToGetAllProjects() {
        RestAssured
                .given()
                .spec(Specifications.authSpec(User.builder()
                        .username("admin")
                        .password("admin").build()))
                .get("/app/rest/projects");
    }
}