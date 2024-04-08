package pages;

import components.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPage extends ExtendedBasePage {
    @FindBy(linkText = "Login form")
    @CacheLookup
    WebElement loginFormButton;

    HeaderComponent header;

    public MainPage(String browser) {
        super(browser);
        PageFactory.initElements(driver, this);
        this.header = new HeaderComponent(driver);
        visit("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    public FactoryLoginPage openLoginPage() {
        click(loginFormButton);
        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
        return new FactoryLoginPage(driver);
    }

    public HeaderComponent header() {
        return header;
    }
}
