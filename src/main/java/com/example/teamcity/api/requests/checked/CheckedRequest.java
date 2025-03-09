package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedRequest;
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
        var createdModel = (T) uncheckedRequest
                .create(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());

        TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
        return createdModel;
    }

    @Override
    public T read(String locator) {
        return (T) uncheckedRequest
                .read(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T update(String locator, BaseModel model) {
        return (T) uncheckedRequest
                .update(locator, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public Object delete(String locator) {
        return uncheckedRequest
                .delete(locator)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
