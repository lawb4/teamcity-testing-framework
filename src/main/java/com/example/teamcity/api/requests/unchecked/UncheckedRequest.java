package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedRequest<T extends BaseModel> extends Request implements CrudInterface {
    public UncheckedRequest(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
    }

    @Override

    public Response create(BaseModel model) {
        return Allure.step("Trying to create %s".formatted(model.getClass().getSimpleName()),
                () -> RestAssured
                        .given()
                        .spec(spec)
                        .body(model)
                        .post(endpoint.getUrl())
        );
    }

    @Override
    public Response read(String locator) {
        return Allure.step("Trying to read %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> RestAssured
                        .given()
                        .spec(spec)
                        .get(endpoint.getUrl() + "/" + locator)
        );
    }

    @Override
    public Response update(String locator, BaseModel model) {
        return Allure.step("Trying to update %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> RestAssured
                        .given()
                        .spec(spec)
                        .body(model)
                        .put(endpoint.getUrl() + "/" + locator));
    }

    @Override
    public Response delete(String locator) {
        return Allure.step("Trying to delete %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> RestAssured
                        .given()
                        .spec(spec)
                        .delete(endpoint.getUrl() + "/" + locator));
    }
}
