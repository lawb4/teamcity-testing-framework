package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
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

    public static String getStringWithLatinLettersAndDigits() {
        String str;
        do {
            str = TEST_PREFIX + RandomStringUtils.randomAlphanumeric(MAX_LENGTH);
        } while (!str.matches(".*[a-zA-Z].*") || !str.matches(".*\\d.*"));
        return str;
    }

    public static String getStringWithCyrillic() {
        return TEST_PREFIX + RandomStringUtils.random(MAX_LENGTH, 0x0410, 0x0450, false, false);
    }

    public static String getStringStartingWithDigit() {
        return RandomStringUtils.randomNumeric(1) + TEST_PREFIX + RandomStringUtils.randomAlphabetic(MAX_LENGTH);
    }

    public static int getRandomNumberExceedingLimits() {
        return new Random().nextInt(1000 - 226 + 1) + 226;
    }

    public static String getStringWithRepeatingSymbols() {
        char randomSymbol = (char) ('a' + new Random().nextInt(26));
        return TEST_PREFIX + String.valueOf(randomSymbol).repeat(MAX_LENGTH);
    }

    public static Stream<String> invalidCharacters() {
        return Stream.of("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "+", "[", "]", "{", "}", ";", ":", "'", "\"", ",", ".", "/", "<", ">", "?", "_");
    }

    public static Stream<String> invalidCharactersExceptUnderscore() {
        return Stream.of("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "+", "[", "]", "{", "}", ";", ":", "'", "\"", ",", ".", "/", "<", ">", "?");
    }
}