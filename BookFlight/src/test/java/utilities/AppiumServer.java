package utilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import java.io.File;

/*
* I am facing issue while running this on device by running the server
* So for Now this class is not being used.
 */

public class AppiumServer {

    public static AppiumDriverLocalService service;


    public static void startServer(){

        // starting the Appium server code

        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingPort(4788).withIPAddress("127.0.0.1"));
        service.start();
    }

    public static void stopServer(){

        service.stop();

    }
}
