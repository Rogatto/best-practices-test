package google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleMenuActions {

    public WebDriver driver;
    public By searchField = By.name("q");
    public By buttonSearch = By.name("btnK");

    public GoogleMenuActions(WebDriver driver) {
        this.driver = driver;
    }

    public void searchResult(String text) {
        driver.findElement(searchField).sendKeys(text);

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(buttonSearch));

        driver.findElement(buttonSearch).click();
    }
}
