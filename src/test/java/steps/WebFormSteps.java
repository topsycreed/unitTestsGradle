package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebFormSteps {
    public static void openWebFormPage(WebDriver driver) throws InterruptedException {
        WebElement webFormButton = driver.findElement(By.xpath("//a[@href = 'web-form.html']"));
        Thread.sleep(3000);
        webFormButton.click();
    }

    public static void sendInput(WebDriver driver, String text) throws InterruptedException {
        WebElement textInput = driver.findElement(By.cssSelector("#my-text-id"));
        Thread.sleep(3000);
        textInput.sendKeys(text);
        Thread.sleep(3000);
    }
}
