import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.FactoryLoginPage;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

class FromMainLoginPageTests {
    MainPage mainPage;

    @BeforeEach
    void setup() {
        mainPage = new MainPage("chrome");
    }

    @AfterEach
    void teardown() {
        mainPage.quit();
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
        assertThat(login.successBoxPresent()).isFalse();
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }

    @Test
    void testMainPageTitles() {
        assertThat(mainPage.header().getTitleText()).isEqualTo("Hands-On Selenium WebDriver with Java");
        assertThat(mainPage.header().getSubTitleText()).isEqualTo("Practice site");
    }

    @Test
    void testLoginPageTitles() {
        FactoryLoginPage login = mainPage.openLoginPage();
        assertThat(login.header().getTitleText()).isEqualTo("Hands-On Selenium WebDriver with Java");
        assertThat(login.header().getSubTitleText()).isEqualTo("Practice site");
    }
}
