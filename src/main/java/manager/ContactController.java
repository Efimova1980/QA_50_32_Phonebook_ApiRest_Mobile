package manager;

import dto.Token;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BaseApi;
import static io.restassured.RestAssured.given;

public class ContactController implements BaseApi {

    public static Response requestGetAllUserContacts(String token){
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get(GET_ALL_CONTACTS_URL)
                .thenReturn();
    }
}
