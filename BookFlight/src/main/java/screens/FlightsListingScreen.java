package screens;

import Utilities.ScrollUtility;
import base.ScreenBase;
import io.appium.java_client.*;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
/*
* This class responsible for showing listing of flights
* and also find maximum last flight price
*/
public class FlightsListingScreen extends ScreenBase {

    @FindBy(id="com.tajawal:id/toolbarFlightResult")
    public List<MobileElement> flightResultHeader;

    @FindBy(id="com.tajawal:id/quickActionsContainer")
    public List<MobileElement> filtersSelection;

    @FindBy(id="com.tajawal:id/rvFlightResult")
    public List<MobileElement> flightResults;

    @FindBy(id="com.tajawal:id/rvQuickFilter")
    private MobileElement  quickFilter;

    @FindBy(id="com.tajawal:id/tvFlightToolbarSubTitle")
    private MobileElement flightDateAndTravellersInfo;

    @FindBy(id="com.tajawal:id/rvFlightResult")
    private MobileElement flightListingRecyclerView;

    @FindBys({
            @FindBy(id="com.tajawal:id/design_bottom_sheet"),
            @FindBy(id="com.tajawal:id/ltSheetName")
    })
    private List<MobileElement> sortBys;

    // 5 Travellers - 07 Oct - 09 Oct
    @FindBy(id="com.tajawal:id/tvFlightToolbarSubTitle")
    private MobileElement flightTravellersAndReturnDepartureDates;

    private String flightPriceElementID = "com.tajawal:id/tvFinalPrice";
    private ScrollUtility scrollUtility = null;
    private int scrollingOffset = 400;


    public FlightsListingScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectFilter(String filterSelectionText) {
        for (MobileElement element: sortBys) {
            if (element != null && element.getText().equalsIgnoreCase(filterSelectionText)) {
                element.click();
                break;
            }
        }
    }

    public FlightsListingScreen waitForFlightListing() {
        WebDriverWait wait = new WebDriverWait(driver, REQUEST_WAITING_TIMEOUT);
        try {
            wait.until(ExpectedConditions.visibilityOf(quickFilter));
        }catch (Exception exception) {
            System.out.println("There are not flight for the selected dates.");
            return null;
        }
        return this;
    }

    /*
    * Click on Sort by filter screen.
     */
    public FlightsListingScreen selectSortByFilterScreen() {

        List<MobileElement> filters = quickFilter.findElementsById("com.tajawal:id/tvTitle");
        for (MobileElement filter: filters) {
            if (filter.getText().equalsIgnoreCase("Sort By")) {
                filter.click();
                break;
            }
        }
        return this;
    }

    public FlightFilterScreen openFiltersScreen() {
        List<MobileElement> filters = quickFilter.findElementsById("com.tajawal:id/tvTitle");
        for (MobileElement filter: filters) {
            if (filter.getText().equalsIgnoreCase("Filters")) {
                filter.click();
                break;
            }
        }
        return new FlightFilterScreen(driver);
    }

    public void clickFilters() {

    }

    public float findFirstFlightPrice() {
        MobileElement firstFlightPrice = flightListingRecyclerView.findElementById("com.tajawal:id/tvFinalPrice");

        logger.info("First Flight Price is :"
                + firstFlightPrice.getText());
        return removeStringFromPriceLabel(firstFlightPrice.getText());
    }

    /* If there are more than 500 flights then script will slow.
     * So now only getting first and last flight price and check
     * the sorting.
     */
    public float getLastFlight() {
        return removeStringFromPriceLabel(getLastFlightPrice());
    }
    protected List<MobileElement> findElementsAfterWait(By byElm, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(byElm));
            return driver.findElementsById(flightPriceElementID);
        }   catch (Exception e) {
            return null;
        }
    }

    public String getLastFlightPrice() {

        if (scrollUtility == null) {
            scrollUtility = new ScrollUtility(driver, flightPriceElementID, 500);
        }
        while (!scrollUtility.isScrollingFinish) {
            scrollUtility.swipeDown(scrollingOffset);
        }
        System.out.println("Scrolling finishes for searching last flight");
        List<MobileElement> elements = findElementsAfterWait(By.id(flightPriceElementID), 1);
        int size = elements.size();

        MobileElement lastFlight = elements.get(size-1);
        return lastFlight.getText();
    }

    public String getTravellersCountAndDepartureReturnDate() {
        return flightTravellersAndReturnDepartureDates.getText();
    }

    public boolean isExceptionOccurerdDuringScrolling() {
        return scrollUtility.isExceptionOccueredDuringScrolling;
    }

}
