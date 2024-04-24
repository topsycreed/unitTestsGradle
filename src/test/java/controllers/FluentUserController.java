package controllers;

import configurations.TestConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;
import static testdata.ApiTestData.DEFAULT_USER;

public class FluentUserController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public FluentUserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default user")
    public HttpResponse addDefaultUser() {
        this.requestSpecification.body(DEFAULT_USER);
        return new HttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Add user")
    public HttpResponse addUser(User user) {
        this.requestSpecification.body(user);
        return new HttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Get user by name")
    public HttpResponse getUserByName(String name) {
        return new HttpResponse(given(this.requestSpecification).get(String.format("user/" + name)).then());
    }

    @Step("Delete user by name")
    public HttpResponse deleteUserByName(String name) {
        return new HttpResponse(given(this.requestSpecification).delete(String.format("user/" + name)).then());
    }
}
