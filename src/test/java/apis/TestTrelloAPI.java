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
    private final String projectPath = System.getProperty("user.dir");
    private String hostTrello;

    @Before
    public void setUp() throws IOException {

        InputStream input = new FileInputStream(projectPath + "/properties/project.properties");
        Properties properties = new Properties();

        properties.load(input);

        apiKey = properties.getProperty("api_key_trello");
        accessToken = properties.getProperty("token_trello");
        hostTrello = properties.getProperty("endpoint_trello");
    }

    @Test
    public void testTrelloAPI(){

        /* adiciona um board ao Trello */
        Response responsePostBoard =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("name", "TDC SP 2020")
                        .queryParam("key", apiKey)
                        .queryParam("token", accessToken)
                        .when()
                        .post(hostTrello + "/boards")
                        .then()
                        .statusCode(200)
                        .extract().
                        response();

        responsePostBoard.print();

        JsonPath pathjsonBoard = responsePostBoard.getBody().jsonPath();
        String idBoard = pathjsonBoard.get("id");

        /* adiciona uma lista ao Trello */
        Response responsePostList =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("name", "As 8 melhores práticas e formas de simplificar e estruturar todos seus Testes Automatizados")
                        .queryParam("idBoard", idBoard)
                        .queryParam("key", apiKey)
                        .queryParam("token", accessToken)
                        .when()
                        .post(hostTrello + "/lists")
                        .then()
                        .statusCode(200)
                        .extract().
                        response();

        responsePostList.print();

        JsonPath pathjsonList = responsePostList.getBody().jsonPath();
        String idList = pathjsonList.get("id");

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
