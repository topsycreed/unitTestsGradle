import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasicLoginPage;
import pages.ExtendedLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

class ExtendedLoginPageTests {
    ExtendedLoginPage login;

    @BeforeEach
    void setup() {
        login = new ExtendedLoginPage("chrome");
    }

    @AfterEach
    void teardown() {
        login.quit();
    }

    @Test
    void testLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }

    @Test
    void testLoginFailure() {
        login.with("test", "test");
        assertThat(login.successBoxPresent()).isFalse();
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }
}
