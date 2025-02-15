package com.example.teamcity;

import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssertions softly;
    protected CheckedRequests superUserCheckedRequests = new CheckedRequests(Specifications.superUserAuthSpec());
    protected TestData testData;

    @BeforeEach
    public void beforeTest() {
        softly = new SoftAssertions();
        testData = generate();
    }

    @AfterEach
    public void afterTest() {
        softly.assertAll();
    }
}
