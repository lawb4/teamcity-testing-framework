package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedRequest<T extends BaseModel> extends Request implements CrudInterface {
    public UncheckedRequest(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
    }

    @Override
    //@Step("Create {endpoint.modelClass.simpleName} (Unchecked)")
    @Step("(Unchecked) Create {0}")
    public Response create(BaseModel model) {
        return RestAssured
                .given()
                .spec(spec)
                .body(model)
                .post(endpoint.getUrl());
//        return Allure.step("(Unchecked) Create " + model.getClass().getSimpleName(), () -> {
//            return RestAssured
//                    .given()
//                    .spec(spec)
//                    .body(model)
//                    .post(endpoint.getUrl());
//        });
    }

    @Override
    //@Step("Read {endpoint.modelClass.simpleName} with locator: {locator} (Unchecked)")
    @Step("(Unchecked) Read {endpoint.modelClass.simpleName} with locator: {0}")
    public Response read(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .get(endpoint.getUrl() + "/" + locator);

//        return Allure.step("(Unchecked) Read " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
//            return RestAssured
//                    .given()
//                    .spec(spec)
//                    .get(endpoint.getUrl() + "/" + locator);
//        });
    }

    @Override
    //@Step("Update {endpoint.modelClass.simpleName} with locator: {locator} and data: {model} (Unchecked)")
    @Step("(Unchecked) Update {1} with locator: {0}")
    public Response update(String locator, BaseModel model) {
        return RestAssured
                .given()
                .spec(spec)
                .body(model)
                .put(endpoint.getUrl() + "/" + locator);
//        return Allure.step("(Unchecked) Update " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
//            return RestAssured
//                    .given()
//                    .spec(spec)
//                    .body(model)
//                    .put(endpoint.getUrl() + "/" + locator);
//        });
    }

    @Override
    //@Step("Delete {endpoint.modelClass.simpleName} with locator: {locator} (Unchecked)")
    @Step("(Unchecked) Delete {endpoint.modelClass.simpleName} with locator: {locator}")
    public Response delete(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/" + locator);
//        return Allure.step("(Unchecked) Delete " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
//            return RestAssured
//                    .given()
//                    .spec(spec)
//                    .delete(endpoint.getUrl() + "/" + locator);
//        });
    }
}
