import extensions.AllureExtension;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.FactoryLoginPage;
import pages.MainPage;
import steps.BaseSteps;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Extensions")
@ExtendWith(AllureExtension.class)
class ExtensionTests extends BaseSteps {
    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage(getDriver());
    }

    @Test
    void testLoginSuccess() {
        FactoryLoginPage login = mainPage.openLoginPage();
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }

    @Test
    void testLoginFailure() {
        FactoryLoginPage login = mainPage.openLoginPage();
        login.with("test", "test");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }
}
