import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.FactoryLoginPage;
import pages.FluentLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

class FluentLoginPageTests {
    FluentLoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = new FluentLoginPage("chrome");
    }

    @AfterEach
    void teardown() {
        loginPage.quit();
    }

    @Test
    void testLoginSuccess() {
        loginPage
                .login("user", "user")
                .checkSuccessBoxPresent()
                .checkInvalidCredentialsBoxIsNotPresent();
    }

    @Test
    void testLoginFailure() {
        loginPage
                .login("test", "test")
                .checkInvalidCredentialsBoxPresent()
                .checkSuccessBoxIsNotPresent();
    }
}
