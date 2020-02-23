package apis;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestTrelloAPI {

    private String apiKey;
    private String accessToken;
    private String projectPath = System.getProperty("user.dir");

    @Before
    public void setUp() throws IOException {

        InputStream input = new FileInputStream(projectPath + "/properties/project.properties");
        Properties properties = new Properties();

        properties.load(input);

        apiKey = properties.getProperty("api_key_trello");
        accessToken = properties.getProperty("token_trello");
    }

    @Test
    public void testTrelloAPI(){

        String hostTrello = "https://api.trello.com/1";

        /* adiciona uma lista ao Trello */
        Response responsePostList =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("name", "As 8 melhores práticas e formas de simplificar e estruturar todos seus Testes Automatizados")
                        .queryParam("idBoard", "5e4c8f50f359a505255f1f29")
                        .queryParam("key", apiKey)
                        .queryParam("token", accessToken)
                        .when()
                        .post(hostTrello + "/lists")
                        .then()
                        .statusCode(200)
                        .extract().
                        response();

        responsePostList.print();

        JsonPath pathjson = responsePostList.getBody().jsonPath();

        String idList = pathjson.get("id");

        /* adicionar um card a lista */

        Response responsePostCard =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("idList", idList)
                        .queryParam("keepFromSource", "all")
                        .queryParam("key", apiKey)
                        .queryParam("token", accessToken)
                        .queryParam("name", "Bora Testar")
                        .when()
                        .post(hostTrello + "/cards")
                        .then()
                        .statusCode(200)
                        .extract().
                        response();

        responsePostCard.print();

    }
}
