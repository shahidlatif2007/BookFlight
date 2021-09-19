package screens;

import base.ScreenBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
* This class is responsible for applying lowest price sort.
*/

public class FlightFilterScreen extends ScreenBase {

    @FindBy(id="com.tajawal:id/tvPriceRangeBarFrom")
    private MobileElement filterRangeMinimumPrice;

    @FindBy(id="com.tajawal:id/tvPriceRangeBarTo")
    private MobileElement filterRangeMaximumPrice;

    @AndroidFindBy(id="com.tajawal:id/btnApplyFilter")
    private MobileElement applyFilters;

    private float flightMinimumPrice;
    private float flightMaximumPrice;

    public FlightFilterScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public FlightFilterScreen readMinAndMaxFlightPrices() {
        flightMinimumPrice = removeStringFromPriceLabel(filterRangeMinimumPrice.getText());
        flightMaximumPrice = removeStringFromPriceLabel(filterRangeMaximumPrice.getText());
        return this;
    }

    public float getMinFlightPrice() {
        return flightMinimumPrice;
    }

    public float getMaxFlightPrice() {
        return flightMaximumPrice;
    }

    public void clickBackButton() {
        applyFilters.click();
    }
}
