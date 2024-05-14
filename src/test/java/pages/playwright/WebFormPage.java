package pages.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WebFormPage {
    private final Page page;
    private final Locator submitButton;

    public WebFormPage(Page page) {
        this.page = page;
        this.submitButton = page.getByText("Submit");
    }

    public void submit() {
        submitButton.click();
    }
}
