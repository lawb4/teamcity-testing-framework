package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
import io.qameta.allure.Allure;
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
    public T create(BaseModel model) {
        return Allure.step("Successfully create %s".formatted(model.getClass().getSimpleName()), () -> {
            var createdModel = (T) uncheckedRequest
                    .create(model)
                    .then().assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(endpoint.getModelClass());

            TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
            return createdModel;
        });
    }

    @Override
    public T read(String locator) {
        return Allure.step("Successfully read %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> (T) uncheckedRequest
                        .read(locator)
                        .then().assertThat().statusCode(HttpStatus.SC_OK)
                        .extract().as(endpoint.getModelClass())
        );
    }

    @Override
    public T update(String locator, BaseModel model) {
        return Allure.step("Successfully update %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> (T) uncheckedRequest
                        .read(locator)
                        .then().assertThat().statusCode(HttpStatus.SC_OK)
                        .extract().as(endpoint.getModelClass())
        );
    }

    @Override
    public Object delete(String locator) {
        return Allure.step("Successfully delete %s with locator: %s".formatted(endpoint.getModelClass().getSimpleName(), locator),
                () -> (T) uncheckedRequest
                        .read(locator)
                        .then().assertThat().statusCode(HttpStatus.SC_OK)
                        .extract().as(endpoint.getModelClass())
        );
    }
}
