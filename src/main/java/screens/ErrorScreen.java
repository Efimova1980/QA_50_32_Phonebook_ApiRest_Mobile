package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ErrorScreen extends BaseScreen{
    public ErrorScreen(AppiumDriver driver){
        super(driver);
    }

    @AndroidFindBy(id = "android:id/message")
    WebElement textError;

    @AndroidFindBy(id = "android:id/aerr_close") //message "app keep closing"
    WebElement errClose;
    @AndroidFindBy(id = "android:id/aerr_restart") //message "app has closed"
    WebElement errRestart;


    public boolean isAppCrashed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {//if error 'app keep closing'
            wait.until(ExpectedConditions.visibilityOf(errClose)).isDisplayed();
            return true;
        }catch (TimeoutException e1){
            try{//if error 'app has closed'
                wait.until(ExpectedConditions.visibilityOf(errRestart)).isDisplayed();
                return true;
            }catch (TimeoutException e2){
                return false;
            }
        }
    }

    public  boolean validateTextInError(String text, int time){
        return isTexInElementPresent(textError, text, time);
    }

}
