import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fpmi.Constants.BASE_URL;

class SeleniumCookiesTests {
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
    void cookieTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);
        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        assertThat(username.getPath()).isEqualTo("/");

        driver.findElement(By.id("refresh-cookies")).click();

        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String readValue = options.getCookieNamed(newCookie.getName())
                .getValue();
        assertThat(newCookie.getValue()).isEqualTo(readValue);

        cookies = options.getCookies();
        assertThat(cookies).hasSize(3);

        driver.findElement(By.id("refresh-cookies")).click();

        options.deleteCookie(username);
        assertThat(options.getCookies()).hasSize(cookies.size() - 1);

        driver.findElement(By.id("refresh-cookies")).click();
    }
}
