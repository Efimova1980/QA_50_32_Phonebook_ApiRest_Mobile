package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseScreen {
    protected static AppiumDriver driver;

    public BaseScreen(AppiumDriver driver) {
        BaseScreen.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    //webdriver.wait falls with timeout exception (try-catch to return false)
    public boolean isTexInElementPresent(WebElement element, String text, int time){
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementPresent(WebElement element, int time){
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
}
