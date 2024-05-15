package pages.selenide;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class WebFormPage {
    public void submit() {
        $(By.xpath("//button[text() = 'Submit']")).click();
    }
}
