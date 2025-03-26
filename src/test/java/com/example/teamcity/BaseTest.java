package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static com.example.teamcity.api.spec.Specifications.superUserAuthSpec;

public class BaseTest {
    protected SoftAssertions softly;
    protected UncheckedRequests superUserUncheckedRequests = new UncheckedRequests(superUserAuthSpec());
    protected CheckedRequests superUserCheckedRequests = new CheckedRequests(superUserAuthSpec());
    protected TestData testData;

    @BeforeEach
    public void beforeTest() {
        softly = new SoftAssertions();
        testData = generate();
    }

    @AfterEach
    public void afterTest() {
        softly.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();
    }
}
