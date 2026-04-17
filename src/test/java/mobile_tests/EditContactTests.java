package mobile_tests;

import dto.Contact;
import dto.ContactsDto;
import dto.Token;
import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.*;
import utils.BaseApi;
import static utils.ContactFactory.*;

import static utils.PropertiesReader.getProperty;

public class EditContactTests extends TestBase{

    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    Token token;
    ContactsDto listContacts;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        token = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL)
                .as(Token.class);
        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        if (response.getStatusCode() == 200){
            listContacts = response.as(ContactsDto.class);
            if(listContacts.getContacts().isEmpty()){
                ContactController.requestCreateContact(token.getToken(), positiveContact()); //create contact
            }
        }
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);

    }

    @Test
    public void EditFirstContactPositiveTest(){
        Contact contact = positiveContact();
        contactListScreen.EditFirstContact();
        EditContactScreen editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditNewContactForm(contact);
        editContactScreen.clickBtnUpdate();

        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        if (response.getStatusCode() == 200)
            listContacts = response.as(ContactsDto.class);
        softAssert.assertTrue(contactListScreen.isTextContactUpdatedPresent("Contact was updated!"), "validate message");
        softAssert.assertEquals(listContacts.getContacts().get(0), contact, "validate contact updated");
        softAssert.assertAll();
    }

    @Test
    public void EditFirstContactNegativeTest_EmptyFieldPhone(){
        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        listContacts = response.as(ContactsDto.class);
        Contact contact =  listContacts.getContacts().get(0);
        contact.setPhone("");
        contactListScreen.EditFirstContact();
        EditContactScreen editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditNewContactForm(contact);
        editContactScreen.clickBtnUpdate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Phone number must contain only digits! And length min 10, max 15!", 3));
    }

    @Test
    public void EditFirstContactNegativeTest_EmptyFieldName(){
        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        listContacts = response.as(ContactsDto.class);
        Contact contact =  listContacts.getContacts().get(0);
        contact.setName("");
        contactListScreen.EditFirstContact();
        EditContactScreen editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditNewContactForm(contact);
        editContactScreen.clickBtnUpdate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("must not be blank", 3));
    }

}
