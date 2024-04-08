package pages;

import components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FactoryLoginPage extends ExtendedBasePage {
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

    HeaderComponent header;

    public FactoryLoginPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        header = new HeaderComponent(driver);
        visit("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }

    public FactoryLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        header = new HeaderComponent(driver);
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

    public HeaderComponent header() {
        return header;
    }
}
