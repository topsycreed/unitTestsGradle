import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeleniumTests {
    WebDriver driver;

    @BeforeEach
    void init() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void close() {
        driver.close();
    }

    @Test
    void openSiteTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openForm() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement webFormButton = driver.findElement(By.xpath("//a[@href='web-form.html']"));
        webFormButton.click();
        WebElement actualH1 = driver.findElement(By.xpath("//h1[@class='display-6']"));
        assertEquals("Web form", actualH1.getText());
    }

    @Test
    void openForm2() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement chapter3 = driver.findElement(By.xpath("(//div[@class = 'card-body'])[1]"));
        WebElement webFormButton = chapter3.findElement(By.xpath("//a[contains(@class, 'btn')][1]"));
        webFormButton.click();
        WebElement actualH1 = driver.findElement(By.xpath("//h1[@class='display-6']"));
        assertEquals("Web form", actualH1.getText());
    }

    @Test
    void openForm3() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        List<WebElement> chapter3 = driver.findElements(By.xpath("//div[@class = 'card-body']"));
        List<WebElement> webFormButton = chapter3.get(0).findElements(By.xpath("//a[contains(@class, 'btn')]"));
        webFormButton.get(0).click();
        WebElement actualH1 = driver.findElement(By.xpath("//h1[@class='display-6']"));
        assertEquals("Web form", actualH1.getText());
    }

    @Test
    void checkTextareaInput() {
        openForm();
        WebElement textarea = driver.findElement(By.name("my-textarea"));
        textarea.sendKeys("Test\nTest2");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        WebElement h1 = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        assertEquals("Form submitted", h1.getText());
    }

    @Test
    void disabledInput() {
        this.openForm();
        WebElement disabled = driver.findElement(By.name("my-disabled"));
        assertEquals("", disabled.getText());
        Exception thrown = assertThrows(ElementNotInteractableException.class, () -> disabled.sendKeys("Test\n"));
        assertThat(thrown.getMessage()).contains("element not interactable");
    }

    @Test
    void openCalc() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.findElement(By.xpath("//a[text()='Slow calculator']")).click();
        WebElement title = driver.findElement(By.xpath("//h1[@class='display-6']"));
        assertEquals("Slow calculator", title.getText());
    }

    @Test
    void checkCalcSum() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        //locators
        By calcButtonLocator = By.xpath("//a[text()='Slow calculator']");
        By oneButtonLocator = By.xpath("//div[@class='keys']/span[text()='1']");
        By plusButtonLocator = By.xpath("//div[@class='keys']/span[text()='+']");
        By equalButtonLocator = By.xpath("//div[@class='keys']/span[text()='=']");
        By resultField = By.xpath("//div[@class='screen']");

        //actions
        driver.findElement(calcButtonLocator).click();
        driver.findElement(oneButtonLocator).click();
        driver.findElement(plusButtonLocator).click();
        driver.findElement(oneButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        //assertions
        Thread.sleep(5000);
        assertEquals("2", driver.findElement(resultField).getText());
    }
}
