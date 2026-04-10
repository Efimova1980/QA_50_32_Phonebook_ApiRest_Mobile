package mobile_tests;

import dto.ContactsDto;
import dto.Token;
import dto.User;
import io.appium.java_client.AppiumDriver;
import io.restassured.response.Response;
import manager.AuthenticationController;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;
import utils.BaseApi;

import static utils.PropertiesReader.getProperty;

public class DeleteContactTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    Token token;
    ContactsDto contactsDtoBeforeDel, contactsDtoAfterDel;

    @BeforeMethod
    public void login(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));

        token = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL)
                        .as(Token.class);
        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        System.out.println(response.getStatusLine());

        if (response.getStatusCode() == 200)
            contactsDtoBeforeDel = response.as(ContactsDto.class);

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);

    }

    @Test
    public void deleteContactPositiveTest(){
        int sizeBefore = contactsDtoBeforeDel.getContacts().size();
        contactListScreen.deleteContactMiddle();
        int sizeAfter = ContactController.requestGetAllUserContacts(token.getToken())
                .as(ContactsDto.class).getContacts().size();
        System.out.println(sizeBefore + " - " + sizeAfter);
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    public void deleteFirstPositiveTest(){
        int sizeBefore = contactsDtoBeforeDel.getContacts().size();
        contactListScreen.deleteFirstContact();
        int sizeAfter = ContactController.requestGetAllUserContacts(token.getToken())
                .as(ContactsDto.class).getContacts().size();
        System.out.println(sizeBefore + " - " + sizeAfter);
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }
}
