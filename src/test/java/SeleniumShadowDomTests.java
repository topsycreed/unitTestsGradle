import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fpmi.Constants.BASE_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeleniumShadowDomTests {
    WebDriver driver;

    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testShadowDom() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("p")));
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }
}
