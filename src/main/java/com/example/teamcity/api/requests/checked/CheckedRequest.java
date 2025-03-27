package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

@SuppressWarnings("unchecked")
public class CheckedRequest<T extends BaseModel> extends Request implements CrudInterface {
    private UncheckedRequest uncheckedRequest;

    public CheckedRequest(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.uncheckedRequest = new UncheckedRequest(spec, endpoint);
    }

    @Override
    //@Step("Create {0}")
    public T create(BaseModel model) {
//        var createdModel = (T) uncheckedRequest
//                .create(model)
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().as(endpoint.getModelClass());
//
//        TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
//        return createdModel;
        // Use Allure lifecycle to explicitly create a step
        return Allure.step("Create " + model.getClass().getSimpleName(), () -> {
            var createdModel = (T) uncheckedRequest
                    .create(model)
                    .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(endpoint.getModelClass());

            TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
            return createdModel;
        });
    }

    @Override
    //@Step("Read {endpoint.modelClass.simpleName} with locator: {locator}")
    public T read(String locator) {
//        return (T) uncheckedRequest
//                .read(locator)
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().as(endpoint.getModelClass());
        return Allure.step("Read " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
            return (T) uncheckedRequest
                    .read(locator)
                    .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(endpoint.getModelClass());
        });
    }

    @Override
    //@Step("Update {endpoint.modelClass.simpleName} with locator: {locator} and data: {model}")
    public T update(String locator, BaseModel model) {
//        return (T) uncheckedRequest
//                .update(locator, model)
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().as(endpoint.getModelClass());
        return Allure.step("Update " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
            return (T) uncheckedRequest
                    .read(locator)
                    .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(endpoint.getModelClass());
        });
    }

    @Override
    //@Step("Delete {endpoint.modelClass.simpleName} with locator: {locator}")
    public Object delete(String locator) {
//        return uncheckedRequest
//                .delete(locator)
//                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
//                .extract().asString();
        return Allure.step("Delete " + endpoint.getModelClass().getSimpleName() + " with locator: " + locator, () -> {
            return (T) uncheckedRequest
                    .read(locator)
                    .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(endpoint.getModelClass());
        });
    }
}
