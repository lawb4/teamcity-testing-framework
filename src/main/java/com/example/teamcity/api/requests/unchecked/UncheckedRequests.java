package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

import java.util.EnumMap;

public class UncheckedRequests {
    private final EnumMap<Endpoint, UncheckedRequest> requests = new EnumMap<>(Endpoint.class);

    public UncheckedRequests(RequestSpecification spec) {
        for (var endpoint : Endpoint.values()) {
            requests.put(endpoint, new UncheckedRequest(spec, endpoint));
        }
    }

    public UncheckedRequest getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }
}
