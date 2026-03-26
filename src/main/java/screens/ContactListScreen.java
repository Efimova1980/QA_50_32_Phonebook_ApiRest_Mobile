package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactListScreen extends BaseScreen{
    public ContactListScreen(AppiumDriver driver){
        super(driver);
    }

    @AndroidFindBy(id ="com.sheygam.contactapp:id/emptyTxt")
    WebElement noContacts;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/add_contact_btn")
    WebElement btnAdd;


    public boolean validateTextInContactListScreeenAfterReg(String text, int time){
        return isTexInElementPresent(noContacts, text, time);
    }

    public boolean isBtnAddVisible(){
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(btnAdd)).isDisplayed();
    }
}
