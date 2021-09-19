package utilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/*
* This class read configuration of the application from property
* file and initialize Appium Driver.
*/

public class CommonUtils {

    private static MobileDriver<MobileElement> driver;
    private static URL serverUrl;
    private static int APPIUM_PORT;
    private static int IMPLICIT_WAIT_TIME = 10;
    private static int EXPLICIT_WAIT_TIME = 10;
    private static String APPLICATION_FILE_NAME = "";
    private static String APP_ACTIVITY = "";
    private static String APP_PATH = "";
    private static String PLATFORM_NAME = "";
    private static String PLATFORM_VERSION = "";
    private static String DEVICE_NAME = "";
    private static String APPLICATION_PACKAGE = "";
    private static String ANDROID_UI_AUTOMATOR = "";
    private static DesiredCapabilities capabilities = new DesiredCapabilities();

    private static Properties prop = new Properties();

    public static void main(String[] args) {
        loadAndroidConfProp("android_flight_booking.properties");
    }
    public static void loadAndroidConfProp(String propertyFileName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/" + propertyFileName);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("implicit.wait"));
        EXPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("explicit.wait"));
        APPLICATION_FILE_NAME = prop.getProperty("base.pkg");
        APP_ACTIVITY = prop.getProperty("application.activity");
        PLATFORM_NAME = prop.getProperty("platform.name");
        PLATFORM_VERSION = prop.getProperty("platform.version");
        DEVICE_NAME = prop.getProperty("device.name");
        APPIUM_PORT = Integer.parseInt(prop.getProperty("appium.port"));
        APP_PATH = System.getProperty("user.dir") + "/src/test/resources/AppBuilds/" + APPLICATION_FILE_NAME;
        APPLICATION_PACKAGE = prop.getProperty("application.package.name");
        ANDROID_UI_AUTOMATOR = prop.getProperty("android_ui_automator");
    }
    // Setting Android Capabilities
    public static void setAndroidCapabilities() {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ANDROID_UI_AUTOMATOR);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APPLICATION_PACKAGE);
        File file = new File(APP_PATH);
        String mPath = file.getAbsolutePath();
        capabilities.setCapability(MobileCapabilityType.APP, mPath);
    }
    
    public static void setiOSCapabilities() {

    }

    public static void loadiOSConfProp(String propertyFileName) {

    }

    public static MobileDriver<MobileElement> getAndroidDriver() throws MalformedURLException {
        serverUrl = new URL("http://localhost:" + APPIUM_PORT + "/wd/hub");
        driver = new AndroidDriver<MobileElement>(serverUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
        return driver;
    }

    public static MobileDriver<MobileElement> getiOSDriver() {
        driver = new IOSDriver<MobileElement>(serverUrl, capabilities);
        return driver;
    }
}
