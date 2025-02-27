package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {
    public final static String PROJECT_VIEWER = "PROJECT_VIEWER";
    public final static String PROJECT_DEVELOPER = "PROJECT_DEVELOPER";
    public final static String AGENT_MANAGER = "AGENT_MANAGER";

    @Builder.Default
    private String roleId = "SYSTEM_ADMIN";
    @Builder.Default
    private String scope = "g";

}
