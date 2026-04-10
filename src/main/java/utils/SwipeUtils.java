package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public interface SwipeUtils {
    default void swipeScreen(AppiumDriver driver, Direction direction){
        Dimension size = driver.manage().window().getSize();
        int startX, endX;
        int startY, endY;
        int middleX = size.width/2, middleY = size.height/2;

        switch (direction){
            case RIGHT -> {
                startX = (int)(size.width * 0.2);
                endX = (int)(size.width * 0.8);
                startY = endY = middleY;
            }
            case LEFT -> {
                startX = (int)(size.width * 0.8);
                endX = (int)(size.width * 0.2);
                startY = endY = middleY;
            }
            case DOWN -> {
                startY = (int)(size.height * 0.1);
                endY = (int)(size.height * 0.9);
                startX = endX = middleX;
            }
            case UP -> {
                startY = (int)(size.height * 0.9);
                endY = (int)(size.height * 0.1);
                startX = endX = middleX;
            }
            default -> throw new IllegalArgumentException("Wrong direction --> " + direction);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        //viewport = visible part of the screen
        //put finger down -> move from one point to another -> finger up (imitation with mouse)
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); //asArg - translates to format which undestanding for driver
        driver.perform(Collections.singletonList(swipe));
    }

    default void swipeInsideElement(AppiumDriver driver, WebElement element, Direction direction){
        Rectangle rect = element.getRect();

        int startX, endX;
        int middleY = rect.y + rect.height / 2;

        switch (direction){
            case RIGHT -> {
                startX = (int)(rect.width * 0.2);
                endX = (int)(rect.width * 0.8);
            }
            case LEFT -> {
                startX = (int)(rect.width * 0.8);
                endX = (int)(rect.width * 0.2);
            }
            default -> throw new IllegalArgumentException("Wrong direction --> " + direction);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        //viewport = visible part of the screen
        //put finger down -> move from one point to another -> finger up (imitation with mouse)
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, middleY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, middleY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); //asArg - translates to format which undestanding for driver
        driver.perform(Collections.singletonList(swipe));
    }

}
