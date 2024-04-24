import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import models.Pet;
import models.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

class ExtendedAPITests {

    @Test
    void getUserDeserializationTest() {
        String username = "FPMI_user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        User expextedUser = new User(0, "FPMI_user_1", "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0);

        User actualUser = given().when().get(endpoint).as(User.class);
//        User actualUser = given().when().get(endpoint).then().extract().as(User.class);

        assertThat(actualUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(expextedUser);

        //same as
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUser.getFirstName()).isEqualTo(expextedUser.getFirstName());
        softly.assertThat(actualUser.getLastName()).isEqualTo(expextedUser.getLastName());
        softly.assertThat(actualUser.getUsername()).isEqualTo(expextedUser.getUsername());
        softly.assertThat(actualUser.getUserStatus()).isEqualTo(expextedUser.getUserStatus());
        softly.assertThat(actualUser.getEmail()).isEqualTo(expextedUser.getEmail());
        softly.assertThat(actualUser.getPassword()).isEqualTo(expextedUser.getPassword());
        softly.assertAll();
    }

    @Test
    void getComplexResponseDeserializationTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
//        var pets = given().
//                header("accept", "application/json").
//                queryParam("status", "available").
//                when().
//                get(endpoint).
//                as(new TypeRef<List<Pet>>() {
//                });
//        List<Pet> pets = given().
//                    header("accept", "application/json").
//                    queryParam("status", "available").
//                when().
//                    get(endpoint).
//                        as(new TypeRef<>() {
//                        });
        List<Pet> pets = given().
                header("accept", "application/json").
                queryParam("status", "available").
                when().
                get(endpoint).
                then().extract().as(new TypeRef<List<Pet>>() {
                });
        assertThat(pets).hasSizeGreaterThan(100);
    }

    @Test
    void jsonPathTest() {
        User expextedUser = new User(0, "FPMI_user_1", "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0);
        String endpoint = "https://petstore.swagger.io/v2/user/" + expextedUser.getUsername();

        String jsonResponse = given().when().get(endpoint).asString();
        JsonPath jsonPath = from(jsonResponse);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(jsonPath.getLong("id")).isPositive();
        softly.assertThat(jsonPath.getString("firstName")).isEqualTo(expextedUser.getFirstName());
        softly.assertThat(jsonPath.getString("lastName")).isEqualTo(expextedUser.getLastName());
        softly.assertThat(jsonPath.getString("username")).isEqualTo(expextedUser.getUsername());
        //...
        softly.assertAll();
    }

    @Test
    void jsonPathSecondTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
        JsonPath jsonPath = given().
                    header("accept", "application/json").
                    queryParam("status", "available").
                when().
                    get(endpoint).
                then()
                    .extract()
                    .jsonPath();

        List<Long> ids = jsonPath.getList("id", Long.class);
//        List<Long> ids = jsonPath.getList("id").stream().map(id -> Long.parseLong(String.valueOf(id))).toList();
        List<Pet> filteredIds = jsonPath.getList("findAll { it.id < 1000 }", Pet.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(jsonPath.getLong("[0].id")).isPositive();
        softly.assertThat(ids).allMatch(id -> id > 0L);
        softly.assertThat(jsonPath.getString("[1].name")).isNotEmpty();
        softly.assertThat(jsonPath.getString("[2].tags[0].name")).isNotEmpty();
        softly.assertThat(filteredIds.get(0).getId()).isInstanceOf(Long.class);
        //...
        softly.assertAll();
    }

    @Test
    void jsonSchemaTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
        given().
                    header("accept", "application/json").
                    queryParam("status", "available").
                when().
                    get(endpoint).
                then().
                    assertThat().
                    body(matchesJsonSchemaInClasspath("jsonSchema/petSchema.json"));
    }
}
