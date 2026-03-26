package manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

import static utils.PropertiesReader.*;

public class AppiumConfig {
    public static AppiumDriver createAppiumDriver(String fileName){
        /*
        {"platformName": "Android",
        "automationName": "UiAutomator2",
        "deviceName": "Pixel",
        "appPackage": "com.sheygam.contactapp",
        "appActivity": ".SplashActivity"}
         */
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getProperty(fileName, "os"))
                .setAutomationName(getProperty(fileName, "automationName"))
                .setDeviceName(getProperty(fileName, "deviceName"))
                .setAppPackage(getProperty(fileName, "appPackage"))
                .setAppActivity(getProperty(fileName, "appActivity"));
        try {
            return new AppiumDriver(new URL(getProperty(fileName, "appiumUrl")), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad Appium URL" + getProperty(fileName, "appiumUrl") , e);
        }
    }
}
