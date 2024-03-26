import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.fpmi.Constants.BASE_URL;

public class SeleniumLocatorsTests {
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

    @Test
    void baseLocatorsTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        driver.manage().window().fullscreen();
        WebElement textInputById = driver.findElement(By.id("my-text-id"));
        textInputById.sendKeys("textInputById");
        Thread.sleep(1000);
        WebElement textInputByClassName = driver.findElement(By.className("form-control"));
        textInputByClassName.sendKeys("textInputByClassName");
        Thread.sleep(1000);
        WebElement textInputByName = driver.findElement(By.name("my-text"));
        textInputByName.sendKeys("textInputByName");
        Thread.sleep(1000);
        WebElement textInputByTagName = driver.findElement(By.tagName("input"));
        textInputByTagName.sendKeys("textInputByTagName");
        Thread.sleep(1000);
        WebElement titleByLinkText = driver.findElement(By.linkText("Return to index"));
        titleByLinkText.click();
        Thread.sleep(1000);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        driver.manage().window().fullscreen();
        Thread.sleep(1000);
        WebElement titleByPartialLinkText = driver.findElement(By.partialLinkText("Return to index"));
        titleByPartialLinkText.click();
        Thread.sleep(1000);
    }

    @Test
    void cssSelectorsTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement textInputById = driver.findElement(By.cssSelector("#my-text-id"));
        textInputById.sendKeys("textInputById");
        Thread.sleep(1000);
        WebElement textInputByClass = driver.findElement(By.cssSelector(".form-control"));
        textInputByClass.sendKeys("textInputByClass");
        Thread.sleep(1000);
        WebElement textInputByName = driver.findElement(By.cssSelector("[name='my-text']"));
        textInputByName.sendKeys("textInputByName");
        Thread.sleep(1000);
        WebElement textInputByTagAndClass = driver.findElement(By.cssSelector("input.form-control"));
        textInputByTagAndClass.sendKeys("textInputByTagAndClass");
        Thread.sleep(1000);
        WebElement textInputByTagAndId = driver.findElement(By.cssSelector("input#my-text-id"));
        textInputByTagAndId.sendKeys("textInputByTagAndId");
        Thread.sleep(1000);
        WebElement textInputByTagAndAttribute = driver.findElement(By.cssSelector("input[myprop='myvalue']"));
        textInputByTagAndAttribute.sendKeys("textInputByTagAndAttribute");
        Thread.sleep(1000);
    }

    @Test
    void cssSelectorsTest2() throws InterruptedException {
        WebElement prefix = driver.findElement(By.cssSelector("a[href^='web']"));
        prefix.click();
        Thread.sleep(1000);
        driver.get(BASE_URL);
        WebElement postfix = driver.findElement(By.cssSelector("a[href$='html']"));
        postfix.click();
        Thread.sleep(1000);
        driver.get(BASE_URL);
        WebElement substring = driver.findElement(By.cssSelector("a[href*='-form']"));
        substring.click();
        Thread.sleep(1000);
        driver.get(BASE_URL);
        WebElement exactMatch = driver.findElement(By.cssSelector("a[href='web-form.html']"));
        exactMatch.click();
        Thread.sleep(1000);
        driver.get(BASE_URL);
    }

    @Test
    void cssSelectorsChildTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        driver.manage().window().fullscreen();
        Thread.sleep(5000);
        WebElement childCheckbox = driver.findElement(By.cssSelector("label.form-check-label input[type='checkbox']"));
        childCheckbox.click();
        Thread.sleep(1000);
        WebElement nthChildCheckbox = driver.findElement(By.cssSelector("div.form-check label:nth-child(2) input"));
        nthChildCheckbox.click();
        Thread.sleep(1000);
    }

    @Test
    void xpathSelectorsTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement byTag = driver.findElement(By.xpath("//input"));
        byTag.sendKeys("byTag");
        Thread.sleep(1000);
        WebElement byAttribute = driver.findElement(By.xpath("//*[@myprop='myvalue']"));
        byAttribute.sendKeys("byAttribute");
        Thread.sleep(1000);
        WebElement byText = driver.findElement(By.xpath("//h1[text()='Hands-On Selenium WebDriver with Java']"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", byText.getText());
        WebElement byPartialText = driver.findElement(By.xpath("//h1[contains(text(), 'Hands-On Selenium WebDriver')]"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", byPartialText.getText());
        WebElement child = driver.findElement(By.xpath("//label/input[@id='my-check-1']"));
        child.click();
        Thread.sleep(1000);
        WebElement parent = driver.findElement(By.xpath("//input[@id='my-text-id']/.."));
        Assertions.assertEquals("Text input", parent.getText());
        Thread.sleep(1000);
    }
}
