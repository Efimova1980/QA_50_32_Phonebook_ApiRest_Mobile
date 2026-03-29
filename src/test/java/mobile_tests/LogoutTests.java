package mobile_tests;

import dto.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;

import static utils.PropertiesReader.getProperty;

public class LogoutTests extends TestBase{

    @Test
    public void logoutPositiveTest(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        LoginRegistrationScreen loginRegistrationScreen = new LoginRegistrationScreen(driver);
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();

        ContactListScreen contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickDotsMenu();
        contactListScreen.clickMenuLogout();
        Assert.assertTrue(loginRegistrationScreen.isTitleAuthenticationPresent());
    }
}
