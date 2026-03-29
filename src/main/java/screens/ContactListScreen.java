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
    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was added!']")
    WebElement textContactAdded;
    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='More options']")
    WebElement dotsMenu;
    @AndroidFindBy(xpath = "(//android.widget.LinearLayout[@resource-id='com.sheygam.contactapp:id/content'])[1]")
    WebElement menuLogout;


    public boolean validateTextInContactListScreeenAfterReg(String text, int time){
        return isTexInElementPresent(noContacts, text, time);
    }

    public boolean isBtnAddVisible(){
        return isElementPresent(btnAdd, 3);
    }

    public void clickBtnAdd(){
        btnAdd.click();
    }

    public boolean isTextContactAddedPresent(String text){
        return isTexInElementPresent(textContactAdded, text, 3);
    }

    public void clickDotsMenu(){
        dotsMenu.click();
    }

    public void clickMenuLogout(){
        menuLogout.click();
    }
}
