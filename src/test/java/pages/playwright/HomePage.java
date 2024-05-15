package pages.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;
    private final Locator webFormButton;

    public HomePage(Page page) {
        this.page = page;
        this.webFormButton = page.getByText("Web form");
    }

    public void open() {
        page.navigate("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    public WebFormPage openWebFormPage() {
        webFormButton.click();
        return new WebFormPage(page);
    }
}
