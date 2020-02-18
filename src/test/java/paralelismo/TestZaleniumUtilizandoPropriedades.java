package paralelismo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestZaleniumUtilizandoPropriedades {

    private WebDriver driver;
    private String projectPath = System.getProperty("user.dir");
    private String baseUrlGoogle;

    @Before
    public void setUp() throws IOException {

        /* exemplo de como pegar valores do arquivo de propriedades */
        InputStream input = new FileInputStream(projectPath + "/properties/project.properties");
        Properties properties = new Properties();

        properties.load(input);
        String endPointZalenium = properties.getProperty("endpoint_zalenium");
        baseUrlGoogle = properties.getProperty("endpoint_google");

        System.out.println(endPointZalenium);
        System.out.println(baseUrlGoogle);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL remoteWebDriverUrl = new URL(endPointZalenium);
        driver = new RemoteWebDriver(remoteWebDriverUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testZalenium(){

        driver.get(baseUrlGoogle);

        Cookie cookie = new Cookie("zaleniumTestPassed", "true");
        driver.manage().addCookie(cookie);
    }

    @After
    public void tearOff(){
        driver.quit();
    }
}
