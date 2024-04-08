import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasicLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

class BasicLoginPageTests {
    WebDriver driver;
    BasicLoginPage login;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        login = new BasicLoginPage(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testBasicLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
    }

    @Test
    void testBasicLoginFailure() {
        login.with("test", "test");
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }
}
