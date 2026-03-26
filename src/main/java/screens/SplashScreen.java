package screens;

import dto.User;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SplashScreen extends BaseScreen{
    public SplashScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/version_text")
    //@AndroidFindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/version_text']")
    WebElement versionApp;

    public boolean validateVersionApp(String text, int time){
        return isTexInElementPresent(versionApp, text, time);
    }


}
