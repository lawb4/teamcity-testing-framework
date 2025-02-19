package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", "btLocator", BuildType.class),
    PROJECTS("/app/rest/projects", "projectLocator", Project.class),
    USERS("/app/rest/users", "userLocator", User.class);

    private final String url;
    private final String locatorParam;
    private final Class<? extends BaseModel> modelClass;
}
