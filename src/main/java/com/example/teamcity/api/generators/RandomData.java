package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.stream.Stream;

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

    public static String getStringEndsWithSpecialCharacter(char specialCharacter) {
        return TEST_PREFIX + RandomStringUtils.randomAlphabetic(MAX_LENGTH) + specialCharacter;
    }

    public static String getStringStartsWithSpecialCharacter(char specialCharacter) {
        return specialCharacter + TEST_PREFIX + RandomStringUtils.randomAlphabetic(MAX_LENGTH);
    }

    public static String getStringWithCyrillic() {
        return TEST_PREFIX + RandomStringUtils.random(MAX_LENGTH, 0x0410, 0x0450, false, false);
    }

    public static String getStringStartingWithDigit() {
        return RandomStringUtils.randomNumeric(1) + TEST_PREFIX + RandomStringUtils.randomAlphabetic(MAX_LENGTH);
    }

    public static Stream<String> invalidCharacters() {
        return Stream.of("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "+", "[", "]", "{", "}", ";", ":", "'", "\"", ",", ".", "/", "<", ">", "?", "_");
    }

    public static Stream<String> invalidCharactersExceptUnderscore() {
        return Stream.of("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "+", "[", "]", "{", "}", ";", ":", "'", "\"", ",", ".", "/", "<", ">", "?");
    }
}