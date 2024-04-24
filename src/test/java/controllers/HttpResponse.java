package controllers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;

public class HttpResponse {
    private final ValidatableResponse response;

    public HttpResponse(ValidatableResponse response) {
        this.response = response;
    }

    @Step("Check status code")
    public HttpResponse statusCodeIs(int status) {
        this.response.statusCode(status);
        return this;
    }

    @Step("Check json value")
    public HttpResponse jsonValueIs(String path, String expectedValue) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).as("Actual value '%s' is not equals to expected '%s' for the path '%s' and response: \n%s", actualValue, expectedValue, path, this.response.extract().response().andReturn().asPrettyString()).isEqualTo(expectedValue);
        return this;
    }

    public HttpResponse jsonValueIsNotNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).isNotNull();
        return this;
    }

    @Step("Check json value is null")
    public HttpResponse jsonValueIsNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).isNull();
        return this;
    }

    public String getJsonValue(String path) {
        String value = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(value).isNotNull();
        return value;
    }

    @Override
    public String toString() {
        return String.format("Status code: %s and response: \n%s", response.extract().response().statusCode(), response.extract().response().asPrettyString());
    }
}
