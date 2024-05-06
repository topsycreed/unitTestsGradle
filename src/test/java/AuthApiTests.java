import controllers.CartController;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.Item;
import models.Items;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static testdata.ApiTestData.JEANS_ITEM;

@Story("Auth API")
class AuthApiTests {
    CartController controller = new CartController();

    @Test
    void tokenTest() {
        String url = "https://www.ae.com/ugp-api/auth/oauth/v4/token";

        Response response = given().
                when().
                header("Authorization", "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==").
                header("Aesite", "AEO_US").
                formParam("grant_type", "client_credentials").
                post(url).
                then().
                log().
                all().
                assertThat().
                statusCode(200).
                body("scope", equalTo("guest")).
                body("token_type", equalTo("client_credentials")).
                body("expires_in", equalTo(1800))
                .extract().response();
        String token = response.jsonPath().get("access_token");
        System.out.println("Token: " + token);
    }

    @Test
    void addItemTest() {
        int qtyBefore = controller.getBag().jsonPath().get("data.itemCount");

        controller.addItems(JEANS_ITEM);
        int qtyAfter = controller.getBag().jsonPath().get("data.itemCount");

        assertThat(qtyAfter).isGreaterThan(qtyBefore);
    }

    @Test
    void editItemTest() {
        controller.addItems(JEANS_ITEM);
        String itemId = controller.getBag().jsonPath().get("data.items[0].itemId");
        Item updatedItem = JEANS_ITEM.getItems().get(0);
        updatedItem.setItemId(itemId);
        updatedItem.setQuantity(2);
        Items updatedItems = Items.builder()
                .items(List.of(updatedItem))
                .build();

        controller.editItems(updatedItems);
        int qtyAfter = controller.getBag().jsonPath().get("data.itemCount");
        assertThat(qtyAfter).isEqualTo(2);
    }
}
