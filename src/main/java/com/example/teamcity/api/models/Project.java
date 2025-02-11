package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseModel {
    private String id;
    private String name;
    @Builder.Default
    private String locator = "_Root";
}
