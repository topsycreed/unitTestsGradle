package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ExtendedLoginPage extends ExtendedBasePage {
    By usernameInput = By.id("username");
    By passwordInput = By.id("password");
    By submitButton = By.cssSelector("button");
    By successBox = By.id("success");
    By invalidCredentialsBox = By.id("invalid");

    public ExtendedLoginPage(String browser) {
        super(browser);
        visit("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }

    @Step("Login by username and password")
    public void with(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(submitButton);
    }

    @Step("Get login box is present")
    public boolean successBoxPresent() {
        return isDisplayed(successBox);
    }

    @Step("Get invalid credentials box is present")
    public boolean invalidCredentialsBoxPresent() {
        return isDisplayed(invalidCredentialsBox);
    }
}
