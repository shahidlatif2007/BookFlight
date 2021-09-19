package base;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;



public class ScreenBase {

    public static MobileDriver<MobileElement> driver;

    public static Logger logger = Logger.getLogger(ScreenBase.class);

    // As request timeout in most project is 60.
    public int REQUEST_WAITING_TIMEOUT = 60;
    public int ELEMENT_WAITING_TIMEOUT = 10;

    public ScreenBase(MobileDriver<MobileElement> driver) {

        this.driver = driver;
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public void enter() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public int generateRandomNumber(int arrayLength) {
        Random random = new Random();
        return random.nextInt(arrayLength);
    }

    public void debugSleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    * This function will remove currency from String. i.e AED 98,317.00
    * into 98317.
     */
    public float removeStringFromPriceLabel(String price) {
        return Float.parseFloat(price.replaceAll("[^0-9.]", ""));
    }
}
