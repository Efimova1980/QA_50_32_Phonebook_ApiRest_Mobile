package screens;

import dto.Contact;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Direction;
import utils.SwipeUtils;

import java.time.Duration;
import java.util.List;

public class ContactListScreen extends BaseScreen implements SwipeUtils {
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
    @AndroidFindBy(id = "android:id/button1")
    WebElement btnYes;
    @AndroidFindBy(xpath = "(//*[@resource-id='com.sheygam.contactapp:id/rowContainer'])")
    List<WebElement> contactListScreen;

    @AndroidFindBy(xpath = "//android.widget.Toast[@text='Contact was updated!']")
    WebElement textContactUpdated;

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

    public boolean isTextContactUpdatedPresent(String text){
        return isTexInElementPresent(textContactAdded, text, 3);
    }

    public boolean isTextInFirstContactPresent(String text){
        return isTexInElementPresent(textContactAdded, text, 3);
    }

    public void clickDotsMenu(){
        dotsMenu.click();
    }

    public void clickMenuLogout(){
        menuLogout.click();
    }

    public void deleteContactMiddle(){
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOf(btnAdd));
        swipeScreen(driver, Direction.RIGHT);
        btnYes.click();
    }

    public void deleteFirstContact(){
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOf(btnAdd));
        swipeInsideElement(driver, contactListScreen.get(0), Direction.RIGHT);
        btnYes.click();
    }

    public void EditFirstContact() {
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOf(btnAdd));
        swipeInsideElement(driver, contactListScreen.get(0), Direction.LEFT);
    }

}
