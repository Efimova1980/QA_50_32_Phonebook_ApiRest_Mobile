package manager;

import dto.Contact;
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

    public static Response requestCreateContact(String token, Contact contact){
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post(ADD_NEW_CONTACT_URL)
                .thenReturn();
    }
}
