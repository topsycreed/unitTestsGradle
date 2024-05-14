import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.playwright.HomePage;
import pages.playwright.WebFormPage;
import steps.AllureSteps;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("playwright")
class PlaywrightTests {
    AllureSteps allureSteps = new AllureSteps();

    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void successfulLoginTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        Locator subTitle = page.locator(".display-6");
        Locator loginInput = page.locator("#username");
        Locator passwordInput = page.locator("#password");
        Locator submitButton = page.locator("xpath=//button[@type='submit']");

        loginInput.fill("user");
        passwordInput.fill("user");
        String textBeforeClick = subTitle.innerText();
        submitButton.click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        Locator successMessage = page.locator("#success");
        assertThat(successMessage.isVisible()).isTrue();
    }

    @Test
    void openSiteTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/");
        assertEquals("Hands-On Selenium WebDriver with Java", page.title());
    }

    @Test
    void openForm() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/");
        Locator webFormButton = page.locator("xpath=//div[@class = 'card-body']")
                .locator("xpath=.//a[contains(@class, 'btn')]")
                .first();
        webFormButton.click();
        Locator actualH1 = page.locator("css=.display-6");
        assertEquals("Web form", actualH1.innerText());
    }

    @Test
    @DisplayName("Check screenshot attachment")
    void infiniteScrollTestWithAttach() throws InterruptedException, IOException {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");

        page.waitForSelector("xpath=//p");
        int initParagraphsNumber = page.locator("xpath=//p").all().size();

        Locator lastParagraph = page.locator(String.format("xpath=//p[%s]", initParagraphsNumber));
        lastParagraph.evaluate("e => e.scrollIntoView()");

        page.waitForFunction("() => document.querySelectorAll('p').length > " + initParagraphsNumber);
        Thread.sleep(3000);
        allureSteps.captureScreenshotPlaywright(page);
        allureSteps.captureScreenshotPlaywrightSpoiler(page);
    }

    @Test
    void loadingImagesDefaultWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        Locator image = page.locator("#landscape");

        assertThat(image.getAttribute("src")).contains("landscape");
    }

    @Test
    void loadingImagesWithExplicitTimeoutWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        ElementHandle image = page.waitForSelector("#landscape", new Page.WaitForSelectorOptions().setTimeout(10_000));

        assertThat(image.getAttribute("src")).contains("landscape");
    }

    @Test
    void loadingImagesWithCustomTimeoutWaitTest() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        int expectedCount = 4;
        Locator images = page.locator("img");
        while (images.count() != expectedCount) {
            page.waitForTimeout(1000); // Ожидание 1 секунду
        }

        PlaywrightAssertions.assertThat(images).hasCount(expectedCount);
    }

    @Test
    void pageObjectTest() {
        HomePage homePage = new HomePage(page);
        homePage.open();
        WebFormPage webFormPage = homePage.openWebFormPage();
        webFormPage.submit();

        assertThat(page.url()).contains("https://bonigarcia.dev/selenium-webdriver-java/submitted-form.html");
    }
}
