package testdata;

import models.Item;
import models.Items;
import models.User;

import java.util.List;

public class ApiTestData {
    public static final User DEFAULT_USER = new User(0, "FPMI_user_1", "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0);
    public static final User DEFAULT_USER_BUILDER = User.builder()
            .id(0)
            .username("FPMI_user_1")
            .firstName("firstName")
            .lastName("lastName")
            .email("email@gmail.com")
            .password("qwerty")
            .phone("12345678")
            .userStatus(0)
            .build();

    public static final User TEST = User.builder()
            .id(1)
            .username("test")
            .build();

    public static final Items JEANS_ITEM = Items.builder()
            .items(List.of(new Item("0040716474", 1, null)))
            .build();
}
