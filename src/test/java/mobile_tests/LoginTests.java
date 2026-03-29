package mobile_tests;

import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;

import static utils.PropertiesReader.getProperty;

public class LoginTests extends TestBase{
    LoginRegistrationScreen loginRegistrationScreen;
    //-------------------------------------LOGIN TESTS--------------------------------------------------------
    @BeforeMethod
    public void openAuthScreen(){
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test
    public void loginPositiveTest(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ContactListScreen(driver)
                .isBtnAddVisible());
    }

    @Test
    public void loginNegativeTest_EmptyName(){
        User user = new User("",
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }

    @Test
    public void loginNegativeTest_NameBlanked(){
        User user = new User(" ",
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }

    @Test
    public void loginNegativeTest_EmptyFilds(){
        User user = new User("","");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("Login or Password incorrect", 5));
    }
}
