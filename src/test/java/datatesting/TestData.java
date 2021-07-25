package datatesting;

import google.GoogleMenuPages;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    private final String projectPath = System.getProperty("user.dir");
    public WebDriver driver;
    private JsonPath pathjson;

    @Before
    public void setUp() throws IOException {

        InputStream input = new FileInputStream(projectPath + "/properties/project.properties");
        Properties properties = new Properties();

        properties.load(input);

        String endPointSeleniumGrid = properties.getProperty("endpoint_selenium_grid");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL remoteWebDriverUrl = new URL(endPointSeleniumGrid);
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
    public void testDataDrivenTesting() {

        String meetupName = pathjson.get("meetup_name");

        GoogleMenuPages googleActions = new GoogleMenuPages(driver);
        googleActions.searchResult(meetupName);
    }

    @After
    public void tearOFF() {
        driver.quit();
    }
}
