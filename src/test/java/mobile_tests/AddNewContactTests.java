package mobile_tests;

import data_provider.ContactDataProvider;
import dto.Contact;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import static utils.ContactFactory.*;

import static utils.PropertiesReader.getProperty;

public class AddNewContactTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    AddNewContactScreen addNewContactScreen;

    @BeforeMethod
    public void login(){
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickBtnAdd();
        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @Test
    public void createNewContactPositiveTest(){
        Contact contact = positiveContact();
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(contactListScreen.isTextContactAddedPresent("Contact was added!"));
    }

    //In swagger only name, lastname and address are required,
    // but in the documentation - all fields besides description are required

    @Test
    public void createNewContactPositiveTest_OnlyRequiredFields(){
        Contact contact = positiveContactOnlyRequiredFields();
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(contactListScreen.isTextContactAddedPresent("Contact was added!"));
    }

    //-----------------------------homework 21------------------------------------------------------
    @Test
    public void createNewContactNegativeTest_EmptyForm(){
        Contact contact = emptyContact();
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 3));
    }

    //empty fields: name, last name or address -> message "not be blank"
    @Test(dataProvider = "dataProviderFromFile_EmptyField", dataProviderClass = ContactDataProvider.class)
    public void createNewContactNegativeTest_EmptyField(Contact contact){
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("not be blank", 3));
    }
    //-------------------------------invalid phone tests-------------------------------------------
    //empty phone  -> message "min 10, max 15!"
    @Test
    public void createNewContactNegativeTest_EmptyPhoneNumber(){
        Contact contact = positiveContact();
        contact.setPhone("");
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("min 10, max 15!", 3));
    }

    @Test
    public void createNewContactNegativeTest_BlankedPhoneNumber(){
        Contact contact = positiveContact();
        contact.setPhone(" ");
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("min 10, max 15!", 3));
    }

    @Test(dataProvider  = "dataProviderFromFile_WrongPhone", dataProviderClass = ContactDataProvider.class)
    public void createNewContactNegativeTest_WrongPhoneNumber(Contact contact){
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("min 10, max 15!", 3));
    }

    //---------------------------------------invalid mail tests--------------------------------------
    //empty or blank email -> message "must be a well-formed email"
    @Test
    public void createNewContactNegativeTest_EmptyEmail(){
        Contact contact = positiveContact();
        contact.setEmail("");
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        //BUG - returned "contact was added"
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must be a well-formed email", 3));
    }

    @Test
    public void createNewContactNegativeTest_BlankedEmail(){
        Contact contact = positiveContact();
        contact.setEmail(" ");
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must be a well-formed email", 3));
    }

    @Test(dataProvider  = "dataProviderFromFile_WrongMail", dataProviderClass = ContactDataProvider.class)
    public void createNewContactNegativeTest_WrongEmail(Contact contact){
        addNewContactScreen.typeAddNewContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver).validateTextInError("must be a well-formed email", 3));
    }
}
