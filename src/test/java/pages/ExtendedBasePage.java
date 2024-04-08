package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import patterns.WebDriverFactory;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class ExtendedBasePage {
    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 5;

    public ExtendedBasePage(String browser) {
        driver = WebDriverFactory.createWebDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public ExtendedBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void visit(String url) {
        driver.get(url);
    }

    public WebElement find(By element) {
        return driver.findElement(element);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void click(By element) {
        click(find(element));
    }

    public void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void type(By element, String text) {
        type(find(element), text);
    }

    public boolean isDisplayed(WebElement element) {
        return isDisplayed(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayed(ExpectedCondition<?> expectedCondition) {
        try {
            wait.until(expectedCondition);
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for element ", timeoutSec);
            return false;
        }
        return true;
    }
}
