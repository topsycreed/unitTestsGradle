import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

class SimpleAPITests {

    @Test
    void simpleGetInventoryTest() {
        String endpoint = "https://petstore.swagger.io/v2/store/inventory";
        given().when().get(endpoint).then().log().all();
    }

    @Test
    void exampleRestAssuredTest() {
        String jsonBody = """
                {
                  "id": 0,
                  "username": "test_22_04_001",
                  "firstName": "firstName",
                  "lastName": "lastName",
                  "email": "email@gmail.com",
                  "password": "test123",
                  "phone": "phone not exist",
                  "userStatus": 0
                }
                """;
        String url = "https://petstore.swagger.io/v2/user";

        ValidatableResponse response =
                        given().
                            header("accept", "application/json").
                            header("Content-Type", "application/json").
                            body(jsonBody).
                        when().
                            post(url).
                        then();
        Response responseAsResponse = response.extract().response();
        int status = responseAsResponse.statusCode();
        String responseText = responseAsResponse.body().prettyPrint();
        Assertions.assertEquals(200, status);
    }

    @Test
    void simplePostTest() {
        String endpoint = "https://petstore.swagger.io/v2/user";
        String body = """
                {
                  "id": 0,
                  "username": "FPMI_user_1",
                  "firstName": "firstName",
                  "lastName": "lastName",
                  "email": "email@gmail.com",
                  "password": "qwerty",
                  "phone": "12345678",
                  "userStatus": 0
                }
                """;
        var response = given().
                    header("accept", "application/json").
                    header("Content-Type", "application/json").
                    body(body).
                when().
                    post(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

    @Test
    void getUserWithPathParamTest() {
        String username = "FPMI_user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        given().
                when().
                    get(endpoint).
                then().
                    log().
                    all();
    }

    @Test
    void simplePutTest() {
        String username = "FPMI_user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        String body = """
                {
                  "id": 0,
                  "username": "FPMI_user_2",
                  "firstName": "firstName2",
                  "lastName": "lastName2",
                  "email": "email2@gmail.com",
                  "password": "zxcvb",
                  "phone": "987654321",
                  "userStatus": 0
                }
                """;
        var response = given().
                    header("accept", "application/json").
                    header("Content-Type", "application/json").
                    body(body).
                when().
                    put(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

    @Test
    void simpleDeleteTest() {
        String username = "FPMI_user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        var response = given().
                when().
                    delete(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

    @Test
    void postWithModelTest() {
        String endpoint = "https://petstore.swagger.io/v2/user";
        User user = new User(0, "FPMI_user_3", "firstName3", "lastName3", "email3@gmail.com", "qwe123", "123123123", 0);
        var response = given().
                    header("accept", "application/json").
                    header("Content-Type", "application/json").
                    body(user).
                when().
                    post(endpoint).
                then();
        response.log().body();
        response.statusCode(200);

        String username = "FPMI_user_3";
        String endpoint2 = "https://petstore.swagger.io/v2/user/" + username;
        given().
                when().
                    get(endpoint2).
                then().
                    log().
                    all().
                    assertThat().
                    statusCode(200).
                    body("username", equalTo("FPMI_user_3")).
                    body("firstName", startsWith("firstName3")).
                    body("lastName", equalToIgnoringCase("LASTNAME3")).
                    body("email", matchesPattern("^[a-zA-Z0-9._%+-]+@gmail\\.com$")).
                    body("password", equalTo("qwe123")).
                    body("phone", equalTo("123123123"));
    }

    @Test
    void getComplexResponseWithQueryParamTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
        given().
                    header("accept", "application/json").
                    queryParam("status", "available").
                when().
                    get(endpoint).
                then().
                    assertThat().
                    statusCode(200).
                    header("content-type", equalTo("application/json")).
                    body("id", everyItem(notNullValue())).
                    body("name", everyItem(notNullValue())).
                    body("status", everyItem(equalTo("available"))).
                    body("size()", greaterThan(2));
    }
}
