package Utilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.time.Duration;

/*
* This class responsible for scrolling in the scroll element (Recycler or scroll view).
* We provided the find by id of the
* element that we are interested and do the scrolling by swiping.
*/


public class ScrollUtility {

    private MobileDriver<MobileElement> driver;
    public int scrollingAnimationDuration = 0;
    private String scrollElementID = "";
    public String searchedElementID = "";
    public Boolean isScrollingFinish = false;
    public boolean isExceptionOccueredDuringScrolling = false;


    public ScrollUtility(MobileDriver<MobileElement> driver, String scrollElementID) {
        this.driver = driver;
        this.scrollElementID = scrollElementID;
        scrollingAnimationDuration = 3000;
        isExceptionOccueredDuringScrolling = false;
    }

    public ScrollUtility(MobileDriver<MobileElement> driver, String scrollElementID, int scrollingAnimationDuration) {
        this.driver = driver;
        this.scrollElementID = scrollElementID;
        this.scrollingAnimationDuration = scrollingAnimationDuration;
        isExceptionOccueredDuringScrolling = false;

    }
    protected boolean isElementDisplayed(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    public void swipeDown(int pixelsToSwipe) {

        try {
            Point value = null;
            value = driver.findElement(By.id(scrollElementID)).getLocation();

            int x = value.x;
            int y = value.y;
            int y1 = value.y + pixelsToSwipe;

            swipe(x,y1,x,y);

            /* Check After Swipe if element remains same.
            * If same then scroll is at the end.
             */
            value = driver.findElement(By.id(scrollElementID)).getLocation();

            if (x == value.x && y == value.y) {
                isScrollingFinish = true;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            // Due to any exception I am marking scroll finishes because it stuck. Dont have time to fix this.
            // While scrolling there is popup comes if "Do you want to see nearby airports."
            isScrollingFinish = true;
            isExceptionOccueredDuringScrolling = true;
        }

    }

    public void swipeUp(int pixelsToSwipe) {

        try {
            Point value = null;

            value = driver.findElement(By.id(scrollElementID)).getLocation();

            int x = value.x;
            int y = value.y;
            int y1 = value.y+pixelsToSwipe;

            swipe(x, y, x, y1);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            isScrollingFinish = true;
            isExceptionOccueredDuringScrolling = true;
        }

    }

    public void swipe(int fromX,int fromY,int toX,int toY) {

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(fromX,fromY))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(scrollingAnimationDuration)))
                .moveTo(PointOption.point(toX, toY))
                .release()
                .perform();
    }
}
