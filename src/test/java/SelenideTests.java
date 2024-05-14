import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.selenide.HomePage;
import pages.selenide.WebFormPage;
import steps.AllureSteps;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("selenide")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SelenideTests {
    AllureSteps allureSteps = new AllureSteps();

    @BeforeAll
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless"); // without browser interface
        Configuration.browserCapabilities = options;
    }

    @Test
    void successfulLoginTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        SelenideElement subTitle = $(By.className("display-6"));
        WebElement loginInput = $("#username");
        WebElement passwordInput = $("#password");
        WebElement submitButton = $(By.xpath("//button[@type='submit']"));

        loginInput.sendKeys("user");
        passwordInput.sendKeys("user");
        String textBeforeClick = subTitle.getText();
        submitButton.click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        subTitle.shouldHave(text("Login form"));
        WebElement successMessage = $("#success");
        assertThat(successMessage.isDisplayed()).isTrue();
    }

    @Test
    void openSiteTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/");
        assertEquals("Hands-On Selenium WebDriver with Java", title());
    }

    @Test
    void openForm() {
        open("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement webFormButton = $(By.xpath("//div[@class = 'card-body']")).find(By.xpath(".//a[contains(@class, 'btn')]"));
        webFormButton.click();
        SelenideElement actualH1 = $(By.xpath("//h1[@class='display-6']"));
        actualH1.shouldHave(text("Web form"));
    }

    @Test
    @DisplayName("Check screenshot attachment")
    void infiniteScrollTestWithAttach() throws InterruptedException, IOException {
        Selenide.open("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        WebDriver driver = Selenide.webdriver().object();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By pLocator = By.tagName("p");
        List<WebElement> paragraphs = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, 0));
        int initParagraphsNumber = paragraphs.size();

        WebElement lastParagraph = driver.findElement(By.xpath(String.format("//p[%d]", initParagraphsNumber)));
        String script = "arguments[0].scrollIntoView();";
        js.executeScript(script, lastParagraph);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, initParagraphsNumber));
        Thread.sleep(3000);
        allureSteps.captureScreenshotSelenide();
        allureSteps.captureScreenshotSelenideSpoiler();
    }

    @Test
    void loadingImagesDefaultWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        $("#compass").shouldHave(attributeMatching("src", ".*compass.*"));
    }

    @Test
    void loadingImagesWithUpdatedTimeoutWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        Configuration.timeout = 10_000;
        $("#landscape").shouldHave(attributeMatching("src", ".*landscape.*"));
    }

    @Test
    void loadingImagesWithExplicitTimeoutWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        ElementsCollection images = $$("img").filter(visible);
        images.shouldHave(size(4), Duration.ofSeconds(10));
    }

    @Test
    void pageObjectTest() {
        HomePage homePage = new HomePage();
        homePage.open();
        WebFormPage webFormPage = homePage.openWebForm();
        webFormPage.submit();

        Assertions.assertThat(url()).contains("https://bonigarcia.dev/selenium-webdriver-java/submitted-form.html");
    }
}
