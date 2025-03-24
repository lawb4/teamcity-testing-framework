package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

public class Specifications {

    private static RequestSpecBuilder requestBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri("http://" + Config.getProperty("host"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilters(List.of(
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new SwaggerCoverageRestAssured(
                                new FileSystemOutputWriter(Paths.get("target/" + OUTPUT_DIRECTORY))
                        )
                ));
    }

    public static RequestSpecification superUserAuthSpec() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("");
        authScheme.setPassword(Config.getProperty("superUserToken"));

        return requestBuilder().setAuth(authScheme).build();
    }

    public static RequestSpecification unauthSpec() {
        return requestBuilder().build();
    }

    public static RequestSpecification authSpec(User user) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(user.getUsername());
        authScheme.setPassword(user.getPassword());

        return requestBuilder().setAuth(authScheme).build();
    }
}
