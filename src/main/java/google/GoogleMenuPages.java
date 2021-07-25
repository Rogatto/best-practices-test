package google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleMenuPages {

    public WebDriver driver;
    public String hostGoogle = "https://www.google.com.br";

    @FindBy(name = "q")
    public WebElement searchField;

    public GoogleMenuPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get(hostGoogle);
    }

    public void searchResult(String text) {
        searchField.sendKeys(text);
        searchField.submit();
    }
}
