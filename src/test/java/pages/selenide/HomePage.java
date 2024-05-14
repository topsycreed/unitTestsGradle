package pages.selenide;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    public void open() {
        Selenide.open("https://bonigarcia.dev/selenium-webdriver-java/");
    }
    public WebFormPage openWebForm() {
        $(By.linkText("Web form")).click();
        return new WebFormPage();
    }
}
