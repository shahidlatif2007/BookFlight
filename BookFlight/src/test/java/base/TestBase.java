package base;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import utilities.CommonUtils;

import java.net.MalformedURLException;

public class TestBase {

    public MobileDriver<MobileElement> driver;
    public static String loadPorpertyFile = "android_flight_booking.properties";
    public static Logger logger = Logger.getLogger(TestBase.class);

    public void setUp() {
        if (driver == null) {
//            PropertyConfigurator.configure(System.getProperty("usr.dir") + "\\src\\test\\resources\\properties\\");
            PropertyConfigurator
                    .configure(System.getProperty("user.dir") + "//src//test//resources//properties//log4j.properties");
            if (loadPorpertyFile.startsWith("android_")) {
                logger.info("Starting Appium Server");
//                AppiumServer.startServer();
                logger.info("Appium Server Started");

                logger.info("Loading Property File");
                CommonUtils.loadAndroidConfProp(loadPorpertyFile);
                logger.info("Property file loaded");

                CommonUtils.setAndroidCapabilities();

                try {
                    driver = CommonUtils.getAndroidDriver();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    logger.info("Driver Initializing Error." + e.getMessage());
                }
            }
        }
    }

    public void quit() {

    }
}
