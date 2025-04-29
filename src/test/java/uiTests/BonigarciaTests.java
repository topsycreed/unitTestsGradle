package uiTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BonigarciaTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    void openHomePageTest() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openWebFormPageTest() {
        driver.findElement(By.xpath("//a[@href = 'web-form.html']")).click();

        Assertions.assertEquals(BASE_URL + "web-form.html", driver.getCurrentUrl());
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
