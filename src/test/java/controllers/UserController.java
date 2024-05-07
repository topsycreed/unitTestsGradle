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

public class UserController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public UserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.header("api_key", config.getApiKey());
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default user")
    public Response addDefaultUser() {
        this.requestSpecification.body(DEFAULT_USER);
        return given(this.requestSpecification).post("user").andReturn();
    }

    @Step("Add user")
    public Response addUser(User user) {
        this.requestSpecification.body(user);
        return given(this.requestSpecification).post("user").andReturn();
    }

    @Step("Get user by name")
    public Response getUserByName(String username) {
        return given(this.requestSpecification).get("user/" + username).andReturn();
    }

    @Step("Delete user by name")
    public Response deleteUserByName(String username) {
        return given(this.requestSpecification).delete("user/" + username).andReturn();
    }
}
