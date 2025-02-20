package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Optional;
import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {
    @Random
    private String id;
    @Random
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String locator;
    @Optional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Project parentProject;
    @Optional
    private boolean copyAllAssociatedSettings;
}
