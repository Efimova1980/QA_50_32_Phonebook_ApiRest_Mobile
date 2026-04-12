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
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;
import utils.BaseApi;
import utils.ContactFactory;

import static utils.PropertiesReader.getProperty;

public class EditContactTests extends TestBase{

    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    Token token;
    ContactsDto listContacts;

    @BeforeMethod
    public void login(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        token = AuthenticationController.requestRegLogin(user, BaseApi.LOGIN_URL)
                .as(Token.class);

        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);

    }

    @Test
    public void EditFirstContactPositiveTest(){
        Contact contact = ContactFactory.positiveContact();
        contactListScreen.EditFirstContact();
        AddNewContactScreen editContactScreen = new AddNewContactScreen(driver);
        editContactScreen.typeEditNewContactForm(contact);
        editContactScreen.clickBtnUpdate();

        Response response = ContactController.requestGetAllUserContacts(token.getToken());
        if (response.getStatusCode() == 200)
            listContacts = response.as(ContactsDto.class);
        Assert.assertEquals(listContacts.getContacts().get(0), contact);
    }
}
