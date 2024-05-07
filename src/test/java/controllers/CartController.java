package controllers;

import configurations.TestConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Items;

import static io.restassured.RestAssured.given;

public class CartController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public CartController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.header("X-Access-Token", getGuestToken());
        this.requestSpecification.header("Aecountry", "US");
        this.requestSpecification.header("Aelang", "en_US");
        this.requestSpecification.header("Aesite", "AEO_US");
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBagBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Get guest token")
    public String getGuestToken() {
        return given().
                when().
                    header("Authorization", config.getBasicAuth()).
                    header("Aesite", "AEO_US").
                    formParam("grant_type", "client_credentials").
                    post(config.getAuthUrl()).
                then().
                    assertThat().
                    statusCode(200).
                    extract().jsonPath().get("access_token");
    }

    @Step("Add item to bag")
    public Response addItems(Items items) {
        this.requestSpecification.body(items);
        return given(this.requestSpecification)
                .post("/items")
                .then()
                    .statusCode(202)
                    .log().body()
                    .extract().response();
    }

    @Step("Get bag data")
    public Response getBag() {
        return given(this.requestSpecification).get()
                .then()
                    .statusCode(200)
                    .log().body()
                    .extract().response();
    }

    @Step("Edit item in bag")
    public Response editItems(Items items) {
        this.requestSpecification.body(items);
        return given(this.requestSpecification)
                .patch("/items")
                .then()
                    .statusCode(202)
                    .log().body()
                    .extract().response();
    }
}
