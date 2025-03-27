package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;
import io.qameta.allure.Step;

public interface CrudInterface {

    @Step("Create {modelClass} with model: {0}")
    Object create(BaseModel model);

    @Step("Read {modelClass} with id: {0}")
    Object read(String id);

    @Step("Update {modelClass} with id: {0}}")
    Object update(String id, BaseModel model);

    @Step("Delete {modelClass} with id: {0}")
    Object delete(String id);
}
