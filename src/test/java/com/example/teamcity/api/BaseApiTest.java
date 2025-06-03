package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.AuthModules;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.requests.ServerAuthRequest;
import com.example.teamcity.api.spec.Specifications;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseApiTest extends BaseTest {
    private static final ServerAuthRequest serverAuthRequest = new ServerAuthRequest(Specifications.superUserAuthSpec());
    private static AuthModules authModules;
    private static boolean perProjectPermissions;

    @BeforeAll
    public static void setUpServerAuthSettings() {
        // Get current settings perProjectPermissions
        perProjectPermissions = serverAuthRequest.read().getPerProjectPermissions();

        authModules = generate(AuthModules.class);
        // Set value of perProjectPermissions to true
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(true)
                .modules(authModules)
                .build());
    }

    @AfterAll
    public static void cleanUpServerAuthSettings() {
        // Set the default value for perProjectPermissions setting
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(perProjectPermissions)
                .modules(authModules)
                .build());
    }
}