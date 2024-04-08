package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderComponent {
    private WebDriver driver;

    @FindBy(className = "display-4")
    @CacheLookup
    private WebElement title;

    @FindBy(xpath = "//h5[text()='Practice site']")
    @CacheLookup
    private WebElement subTitle;

    @FindBy(className = "img-fluid")
    @CacheLookup
    private WebElement logo;

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitleText() {
        return title.getText();
    }

    public String getSubTitleText() {
        return subTitle.getText();
    }

    public void clickLogo() {
        logo.click();
    }
}
