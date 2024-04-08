package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentLoginPage extends ExtendedBasePage {
    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;

    @FindBy(id = "password")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(css = "button")
    @CacheLookup
    WebElement submitButton;

    @FindBy(id = "success")
    @CacheLookup
    WebElement successBox;

    @FindBy(id = "invalid")
    @CacheLookup
    WebElement invalidCredentialsBox;

    public FluentLoginPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        visit("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }

    public FluentLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public FluentLoginPage login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(submitButton);
        return this;
    }

    public FluentLoginPage checkSuccessBoxPresent() {
        assertThat(isDisplayed(successBox)).isTrue();
        return this;
    }

    public FluentLoginPage checkSuccessBoxIsNotPresent() {
        assertThat(isDisplayed(successBox)).isFalse();
        return this;
    }

    public FluentLoginPage checkInvalidCredentialsBoxPresent() {
        assertThat(isDisplayed(invalidCredentialsBox)).isTrue();
        return this;
    }

    public FluentLoginPage checkInvalidCredentialsBoxIsNotPresent() {
        assertThat(isDisplayed(invalidCredentialsBox)).isFalse();
        return this;
    }
}
