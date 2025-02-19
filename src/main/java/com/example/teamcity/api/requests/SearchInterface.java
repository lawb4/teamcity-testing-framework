package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface SearchInterface<T extends BaseModel> {
    Object search(String locatorValue);
}