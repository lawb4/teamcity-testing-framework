package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface CrudInterface {
    Object create(BaseModel model);

    Object read(String id);

    Object update(String id, BaseModel model);

    Object delete(String id);
}
