package datatesting;

import google.GoogleMenuActions;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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

import static io.restassured.RestAssured.given;

public class TestData {

    private String projectPath = System.getProperty("user.dir");
    public WebDriver driver;
    private String baseUrl;
    private JsonPath pathjson;

    @Before
    public void setUp() throws IOException {

        InputStream input = new FileInputStream(projectPath + "/properties/project.properties");
        Properties properties = new Properties();

        properties.load(input);

        String endPointZalenium = properties.getProperty("endpoint_zalenium");
        baseUrl = properties.getProperty("endpoint_google");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL remoteWebDriverUrl = new URL(endPointZalenium);
        driver = new RemoteWebDriver(remoteWebDriverUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        String apiKey = properties.getProperty("api_key_mockaroo");
        String hostMockaroo = properties.getProperty("endpoint_mockaroo");

        Response response = given()
                .when()
                .get(hostMockaroo + apiKey)
                .then()
                .statusCode(200)
                .extract().
                response();

         response.print();
         pathjson = response.getBody().jsonPath();
    }

    @Test
    public void testDataDrivenTesting() throws InterruptedException {

        String meetupName = pathjson.get("meetup_name");

        driver.get(baseUrl);

        GoogleMenuActions googleActions = new GoogleMenuActions(driver);
            googleActions.searchResult(meetupName);

        Cookie cookie = new Cookie("zaleniumTestPassed", "true");
        driver.manage().addCookie(cookie);
    }

    @After
    public void tearOFF() {
        driver.quit();
    }
}
