package mobile_tests;

import data_provider.UserDataProvider;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import screens.SplashScreen;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.*;

public class RegistrationTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;

    @BeforeMethod
    public void openAuthScreen(){
        new SplashScreen(driver);
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test
    public void registrationPositiveTest(){
        User user = positiveUser();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ContactListScreen(driver)
                .validateTextInContactListScreeenAfterReg("No Contacts. Add One more!", 5));
    }

    @Test
    public void registrationNegativeTest_EmptyEmailTest(){
        User user = positiveUser();
        user.setUsername("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("{username=must not be blank}", 5));
    }

    //-----------------------homework 20--------------------------------------------------

    @Test
    public void registrationNegativeTest_EmptyPasswordTest(){
        User user = positiveUser();
        user.setPassword("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();

//        if(new ErrorScreen(driver).isAppCrashed())
//            Assert.fail("BUG: App crashed (Contacts App has stopped)");

        if(new ErrorScreen(driver).isAppStopDisplay())
            Assert.fail("BUG: App crashed (Contacts App has stopped)");
    }

    @Test
    public void registrationNegativeTest_EmptyFieldsTest(){
        User user = positiveUser();
        user.setPassword("");
        user.setUsername("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();

//        if(new ErrorScreen(driver).isAppCrashed())
//            Assert.fail("BUG: App crashed (Contacts App has stopped)");

        if(new ErrorScreen(driver).isAppStopDisplay())
            Assert.fail("BUG: App crashed (Contacts App has stopped)");
    }

    @Test
    public void registrationNegativeTest_UserAlreadyExists(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("User already exists", 5));
    }

    @Test(dataProvider  = "dataProviderFromFile_WrongPassword", dataProviderClass = UserDataProvider.class)
    public void registrationNegativeTest_WrongPassword(User user){
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("{password= At least 8 characters; Must contain at least 1 uppercase letter, " +
                        "1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}", 5));
    }

    @Test(dataProvider  = "dataProviderFromFile_WrongEmail", dataProviderClass = UserDataProvider.class)
    public void registrationNegativeTest_WrongLogin(User user){
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("{username=must be a well-formed email address}", 5));
    }

}
