package pages;

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

    public void with(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(submitButton);
    }

    public boolean successBoxPresent() {
        return isDisplayed(successBox);
    }

    public boolean invalidCredentialsBoxPresent() {
        return isDisplayed(invalidCredentialsBox);
    }
}
