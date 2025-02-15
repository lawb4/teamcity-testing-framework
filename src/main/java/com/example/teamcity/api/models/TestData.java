package com.example.teamcity.api.models;

import lombok.Data;

@Data
public class TestData {
    private User user;
    private Project project;
    private BuildType buildType;
}
