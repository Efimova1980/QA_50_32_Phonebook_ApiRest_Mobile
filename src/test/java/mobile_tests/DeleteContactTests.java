package mobile_tests;

import dto.Contact;
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
import org.testng.asserts.SoftAssert;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;
import utils.BaseApi;
import static utils.ContactFactory.*;

import static utils.PropertiesReader.getProperty;

public class DeleteContactTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    Token token;
    ContactsDto contactsDtoBeforeDel;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        token = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL).as(Token.class);
        Response response = ContactController.requestGetAllUserContacts(token.getToken());

        if (response.getStatusCode() == 200){
            contactsDtoBeforeDel = response.as(ContactsDto.class);
            if(contactsDtoBeforeDel.getContacts().isEmpty()){
                //create contact
                ContactController.requestCreateContact(token.getToken(), positiveContact());
                //update contactDtoBeforeDell
                contactsDtoBeforeDel = ContactController.requestGetAllUserContacts(token.getToken())
                        .as(ContactsDto.class);
            }
        }
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);

    }

    @Test
    public void deleteContactPositiveTest(){
        int sizeBefore = contactsDtoBeforeDel.getContacts().size();
        contactListScreen.deleteContactMiddle();
        contactListScreen.clickYes();
        int sizeAfter = ContactController.requestGetAllUserContacts(token.getToken())
                .as(ContactsDto.class).getContacts().size();
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    public void deleteFirstContactPositiveTest(){
        int sizeBefore = contactsDtoBeforeDel.getContacts().size();
        contactListScreen.deleteFirstContact();
        contactListScreen.clickYes();
        int sizeAfter = ContactController.requestGetAllUserContacts(token.getToken())
                .as(ContactsDto.class).getContacts().size();
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    public void deleteFirstContactNegativeTest_CancelingDeleting(){
        int sizeBefore = contactsDtoBeforeDel.getContacts().size();
        contactListScreen.deleteFirstContact();
        contactListScreen.clickCancel();
        int sizeAfter = ContactController.requestGetAllUserContacts(token.getToken())
                .as(ContactsDto.class).getContacts().size();
        Assert.assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void deleteFirstContactNegativeTest_CancelingDeletingWithCheckContacts(){
        contactListScreen.deleteFirstContact();
        contactListScreen.clickCancel();
        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        ContactsDto listContacts = response.as(ContactsDto.class);
        softAssert.assertEquals(listContacts.getContacts().size(), contactsDtoBeforeDel.getContacts().size(),
                "Checking if lists have the same size");
        softAssert.assertEquals(listContacts.getContacts(), contactsDtoBeforeDel.getContacts(),
                "checking if first contact is not changed");
        softAssert.assertAll();
    }
}
