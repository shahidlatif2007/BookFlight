package screens;


import base.ScreenBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import java.util.List;


/*
* This class is responsible for selecting select cabing class,
* flight departure and return date.
* Also selecting cabin class type (Economy, Premium etc)/
*/

public class FlightHomeScreen extends ScreenBase {
    @FindBys({
        @FindBy(id="com.tajawal:id/searchToolbar"),
        @FindBy(className="android.widget.TextView")
    })
    private MobileElement searchToolBar;

    @FindBys({
        @FindBy(id = "com.tajawal:id/flightSearchTabLayout"),
        @FindBy(className = "android.widget.TextView")
    })
    public List<MobileElement> tripSelection;

    @FindBys({
            @FindBy(id="com.tajawal:id/originView"),
            @FindBy(id="com.tajawal:id/tvItemText")
    })
    public MobileElement flightOriginSelection;

    @FindBys({
        @FindBy(id="com.tajawal:id/destinationView"),
        @FindBy(id="com.tajawal:id/tvItemText")
    })
    public MobileElement flightDestination;

    @FindBys({
        @FindBy(id="com.tajawal:id/checkIn"),
        @FindBy(id="com.tajawal:id/tvItemText")
    })
    public MobileElement departureDateElement;

    @FindBys({
        @FindBy(id="com.tajawal:id/checkOut"),
        @FindBy(id="com.tajawal:id/tvItemText")
    })
    public MobileElement returnDateElement;

    @FindBy(id="com.tajawal:id/flightPaxView")
    public MobileElement passengerSelection;

    @FindBy(id="com.tajawal:id/viewParent")
    public MobileElement findFlightButton;

    public FlightHomeScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public FlightHomeScreen clickRoundTrip(String journeyType) {
        logger.info("Going to Select Journey trip of Type: " + journeyType);
        for(int index = 0; index < tripSelection.size(); index++) {
            MobileElement element = tripSelection.get(index);
            if (journeyType.equalsIgnoreCase(element.getText())) {
                element.click();
            }
        }
        return this;
    }
    public FlightHomeScreen clickFlightOrigin() {
        flightOriginSelection.click();
        return this;
    }

    public FlightHomeScreen clickCabinClass() {
        passengerSelection.click();
        return this;
    }

    public FlightHomeScreen clickFlightDestination() {
        flightDestination.click();
        return this;
    }

    public void clickStartDate() {
        departureDateElement.click();

    }

    public void searchFlight() {
        findFlightButton.click();
    }

    public String getSelectedOriginAirportName() {
        return flightOriginSelection.getText();
    }

    public String getSelectedDestinationAirportName() {
        return flightDestination.getText();
    }

    public String getDepartureDateString() {
        return departureDateElement.getText();
    }

    public String getReturnDateString() {
        return returnDateElement.getText();
    }

}
