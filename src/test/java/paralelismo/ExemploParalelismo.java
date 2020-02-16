package paralelismo;

import org.junit.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ExemploParalelismo {

    private WebDriver driver;

    @Before
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL remoteWebDriverUrl = new URL("http://localhost:4444/wd/hub");
        driver = new RemoteWebDriver(remoteWebDriverUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testZalenium(){
        driver.get("https://www.google.com.br");

        Cookie cookie = new Cookie("zaleniumTestPassed", "true");
        driver.manage().addCookie(cookie);
    }

    @After
    public void tearOff(){
        driver.quit();
    }
}
