package screens;

import base.ScreenBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
* This class is responsible for selecting book flight in tajawal home page.
*/
public class TajawalHomeScreen extends ScreenBase {

    @FindBys({
        @FindBy(id="com.tajawal:id/flightsCtaView"),
        @FindBy(className="android.widget.TextView")
    })
    public MobileElement bookFlightButton;

    public TajawalHomeScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickBookFlight() {

        WebDriverWait webDriverWait = new WebDriverWait(driver, ELEMENT_WAITING_TIMEOUT);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.tajawal:id/flightsCtaView")));
        bookFlightButton.click();
    }

    public String getBookFlightButtonText() {
        return bookFlightButton.getText();
    }
}
