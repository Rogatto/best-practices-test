package google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleMenuActions {

    public WebDriver driver;
    public By searchField = By.name("q");
    public By buttonSearch = By.name("btnK");

    public GoogleMenuActions(WebDriver driver) {
        this.driver = driver;
    }

    public void searchResult(String text) {
        driver.findElement(searchField).sendKeys(text);
        driver.findElement(buttonSearch).click();
    }
}
