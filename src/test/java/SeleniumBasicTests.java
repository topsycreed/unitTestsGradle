import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.fpmi.Constants.BASE_URL;

@Tag("selenium")
class SeleniumBasicTests {
    WebDriver driver;
    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterEach
    void close() {
        driver.close();
    }

    @Test
    void openUrlTest() {
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openWebFormTest() {
        String xpath = "//a[@href = 'web-form.html']";
        WebElement webFormButton = driver.findElement(By.xpath(xpath));
        webFormButton.click();
        WebElement title = driver.findElement(By.xpath("//h1[@class = 'display-6']"));
        Assertions.assertEquals("Web form", title.getText());
    }
}
