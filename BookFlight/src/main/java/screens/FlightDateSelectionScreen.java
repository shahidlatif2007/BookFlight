package screens;


import Utilities.DateFormatter;
import base.ScreenBase;
import Utilities.DateSelectionUtility;
import io.appium.java_client.*;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
* This class is responsible for selecting
* flight departure and return dates in future.
*/
public class FlightDateSelectionScreen extends ScreenBase {

    @FindBy(id="calendarView")
    public MobileElement element;

    @FindBy(id="com.tajawal:id/fromDateSelection")
    public MobileElement dep;

    @FindBy(id="com.tajawal:id/calendarView")
    public MobileElement scrollView;

    @FindBy(id="com.tajawal:id/fromDateSelection")
    public MobileElement departureSelectedDate;

    @FindBy(id="com.tajawal:id/toDateTV")
    public MobileElement returnSelectedDate;

    @FindBy(id="com.tajawal:id/confirmBtn")
    public MobileElement confirmButton;

    private String randomDepartureDateString = "";
    private String randomReturnDateString = "";


    public FlightDateSelectionScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public FlightDateSelectionScreen selectDepartureAndReturnDateInFuture() {
        DateSelectionUtility dateSelectionUtility = new DateSelectionUtility(driver, scrollView);
        dateSelectionUtility.selectDates();
        randomDepartureDateString = dateSelectionUtility.randomDepatureDateString;
        randomReturnDateString = dateSelectionUtility.randomReturnDateString;
        return this;
    }

    public void clickConfirmButton() {
        confirmButton.click();
    }

    // Return Selected Departure Date (i.e 18 September 2021)

    // Return Departure date for verifying that it is correct.
    public String getRandomDepartureDateString() {
        return DateFormatter.dateConversionFromToDestinationFormat(randomDepartureDateString,
                DateFormatter.dateFormatterInFlightCalendarDateSelection,
                DateFormatter.dateFormatterInFlightInputDetail);

    }
    // Return date for verifying that it is correct.
    public String getRandomReturnDateString() {
        return DateFormatter.dateConversionFromToDestinationFormat(randomReturnDateString,
                DateFormatter.dateFormatterInFlightCalendarDateSelection,
                DateFormatter.dateFormatterInFlightInputDetail) ;
    }
}
