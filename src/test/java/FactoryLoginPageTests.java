import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ExtendedLoginPage;
import pages.FactoryLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryLoginPageTests {
    FactoryLoginPage login;

    @BeforeEach
    void setup() {
        login = new FactoryLoginPage("chrome");
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
