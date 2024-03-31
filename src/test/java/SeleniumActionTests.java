import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fpmi.Constants.BASE_URL;

public class SeleniumActionTests {
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
    void basicTests() throws InterruptedException {
        //click
        WebElement webFormButton = driver.findElement(By.xpath("//a[@href = 'web-form.html']"));
        Thread.sleep(3000);
        webFormButton.click();

        //sendKeys
        WebElement textInput = driver.findElement(By.cssSelector("#my-text-id"));
        Thread.sleep(3000);
        textInput.sendKeys("test");

        //clear
        Thread.sleep(3000);
        textInput.clear();

        //submit by click
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        Thread.sleep(3000);
        submitButton.click();
        Thread.sleep(3000);
        Assertions.assertEquals("Form submitted", driver.findElement(By.cssSelector("h1.display-6")).getText());

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        Thread.sleep(3000);

        //submit by submit
        WebElement submitForm = driver.findElement(By.cssSelector("form[method='get']"));
        Thread.sleep(3000);
        submitForm.submit();
        Assertions.assertEquals("Form submitted", driver.findElement(By.cssSelector("h1.display-6")).getText());
    }

    @Test
    void selectFromListTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        //select by id
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        Thread.sleep(3000);
        select.selectByIndex(1);
        Thread.sleep(3000);
        select.selectByIndex(0);
        //select by value
        Thread.sleep(3000);
        select.selectByValue("2");
        //select by text
        Thread.sleep(3000);
        select.selectByVisibleText("Three");

        //first selected option
        Assertions.assertEquals("Three", select.getFirstSelectedOption().getText());
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());

        //get all selected options
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        for (WebElement selectedOption : selectedOptions) {
            System.out.println("Selected option: " + selectedOption.getText());
        }
        Thread.sleep(3000);

        //get all options
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            System.out.printf("Available Option: %s isSelected = %s%n", option.getText(), option.isSelected());
        }
        Thread.sleep(3000);

        //deselecting
        if (select.isMultiple()) {
            select.deselectByIndex(1);
            select.deselectByValue("1");
            select.deselectByVisibleText("One");
            select.deselectAll();
        } else {
            System.out.println("You may only deselect all options of a multi-select");
        }
        Thread.sleep(3000);
    }

    @Test
    void getInfoTests() {
        //get isDisplayed
        WebElement webFormButton = driver.findElement(By.xpath("//a[@href = 'web-form.html']"));
        Assertions.assertTrue(webFormButton.isDisplayed());
        webFormButton.click();

        //get isEnabled
        WebElement disabledInput = driver.findElement(By.name("my-disabled"));
        Assertions.assertFalse(disabledInput.isEnabled());

        //check exception
        Assertions.assertThrows(ElementNotInteractableException.class, () -> disabledInput.sendKeys("test"));

        //get tag name
        Assertions.assertEquals("input", disabledInput.getTagName());

        //get rect
        Rectangle rec = disabledInput.getRect();
        System.out.printf("Dimension %s, Height %s, Width %s, Point %s, X: %s, Y: %s\n", rec.getDimension(), rec.getHeight(), rec.getWidth(), rec.getPoint(), rec.getX(), rec.getY());

        //get css values
        System.out.println(disabledInput.getCssValue("background-color"));
        System.out.println(disabledInput.getCssValue("opacity"));
        System.out.println(disabledInput.getCssValue("font-size"));
        System.out.println(disabledInput.getCssValue("color"));

        //get text
        Assertions.assertEquals("Disabled input", disabledInput.findElement(By.xpath("..")).getText());

        //get attribute
        Assertions.assertEquals("Disabled input", disabledInput.getAttribute("placeholder"));
    }

    @Test
    void fileUploadTest() throws IOException, InterruptedException {
        String filePath = "src/main/resources/text.txt";

        // Чтение содержимого файла в виде строки
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // Используйте содержимое файла в вашем коде, например, вывод на экран
        System.out.println("Содержимое файла: " + content);

        // Получаем URL ресурса
        URL url = SeleniumActionTests.class.getClassLoader().getResource("text.txt");

        String absolutePath = null;
        if (url != null) {
            // Получаем абсолютный путь к файлу
            absolutePath = new File(url.getPath()).getAbsolutePath();
            System.out.println("Абсолютный путь к файлу: " + absolutePath);
        } else {
            System.out.println("Ресурс не найден.");
        }
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement fileUpload = driver.findElement(By.name("my-file"));
        fileUpload.sendKeys(absolutePath);
        Thread.sleep(5000);
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
        Thread.sleep(5000);
        assertThat(driver.getCurrentUrl()).contains("text.txt");
    }

    @Test
    void actionAPIKeyboardTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement input = driver.findElement(By.id("my-text-id"));
        input.click();

        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .sendKeys("upper-case")
                .keyUp(Keys.SHIFT)
                .sendKeys("lower-case")
                .perform();
        Thread.sleep(2000);
        Assertions.assertEquals("UPPER_CASElower-case", input.getAttribute("value"));

        WebElement password = driver.findElement(By.name("my-password"));
        new Actions(driver)
                .sendKeys(password, "admin123")
                .perform();
        Thread.sleep(2000);
        Assertions.assertEquals("admin123", password.getAttribute("value"));
    }

    @Test
    void actionAPIMouseClickTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        Thread.sleep(2000);

        WebElement dropdown1 = driver.findElement(By.id("my-dropdown-1"));
        new Actions(driver)
                .click(dropdown1)
                .perform();
        Thread.sleep(2000);

        WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
        new Actions(driver)
                .contextClick(dropdown2)
                .perform();
        Thread.sleep(2000);

        WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
        new Actions(driver)
                .doubleClick(dropdown3)
                .perform();
        Thread.sleep(2000);
    }

    @Test
    void actionAPIMouseOverTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        Thread.sleep(2000);

        List<WebElement> images = driver.findElements(By.className("img-fluid"));
        for (WebElement image : images) {
            new Actions(driver)
                    .moveToElement(image)
                    .perform();
            Thread.sleep(1000);
        }
    }

    @Test
    void actionAPIDragAndDropTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        Thread.sleep(2000);

        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("target"));
        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();
        Thread.sleep(2000);
    }

    @Test
    void actionAPIScrollTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        Thread.sleep(2000);

        WebElement footerLink = driver.findElement(By.className("text-muted"));
        new Actions(driver)
                .scrollToElement(footerLink)
                .perform();
        Thread.sleep(2000);
    }
}
