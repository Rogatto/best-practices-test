package paralelismo;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestZaleniumParalelizado {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeClass
    public void setUp(String browser) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(browser.equalsIgnoreCase("chrome")) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        } else if(browser.equalsIgnoreCase("firefox")){
            capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
        }

        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL remoteWebDriverUrl = new URL("http://localhost:4444/wd/hub");
        driver = new RemoteWebDriver(remoteWebDriverUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testZalenium(){
        driver.get("https://www.google.com.br");
    }

    @AfterTest
    public void tearOff(){
        driver.quit();
    }
}