package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomData {
    private static final String TEST_PREFIX = "test_";
    private static final int MAX_LENGTH = 10;

    public static String getString() {
        return TEST_PREFIX + RandomStringUtils.randomAlphabetic(MAX_LENGTH);
    }

    public static String getString(int length) {
        return TEST_PREFIX + RandomStringUtils.randomAlphabetic(
                Math.max(length - TEST_PREFIX.length(), MAX_LENGTH)
        );
    }
}