package screens;

import base.ScreenBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/*
* This class is responsible for selecting airport
* by entering Flight code which is randomly selected.
* This class handle origin and destination airport handling
*/
public class AirportSelectionScreen extends ScreenBase {

    @FindBy(id="com.tajawal:id/edSearch")
    public MobileElement searchFlightField;

    @FindBy(id="com.tajawal:id/airportSearchNestedScrollView")
    public MobileElement searchAirportsList;

    @FindBy(id="com.tajawal:id/rvSearchAirports")
    public MobileElement airports;

    // Input array of airports of source and destination.
    private String[] originAirportList = {"DXB", "AUH", "DMM", "RUH","KHS"};
    private String[] destinationAirportList = {"CAI", "MNL", "LHR", "BER", "KHI", "DEL"};


    // The codes will be DXB, AUH etc
    private String searchedOriginFlightCode;
    private String searchedDestinationFlightCode;

    public String originAirportCode() {
        return searchedOriginFlightCode;
    }

    public String destinationFlightCode() {
        return searchedDestinationFlightCode;
    }

    public AirportSelectionScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void typeAirportCode(String airportName) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, ELEMENT_WAITING_TIMEOUT);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(searchFlightField));
        searchFlightField.click();
        searchFlightField.sendKeys(airportName);
    }

    // Waiting for airport data to be loaded.
    private void waitForAirportResults() {
        WebDriverWait wait = new WebDriverWait(driver, REQUEST_WAITING_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(searchAirportsList));
    }
    //Select airport by searching through list airports.
    private void selectAirportByCode(String airportCode) {

        List <MobileElement> searchedAirportList = airports.findElementsByClassName("android.view.ViewGroup");
        logger.info("Searched airport with code "
                + airportCode
                + " and Searched airport List Count "
                + searchedAirportList.size());
        for (int flightIndex = 0; flightIndex < searchedAirportList.size(); flightIndex++) {

            try {
                MobileElement airportName = searchedAirportList
                        .get(flightIndex)
                        .findElementById("com.tajawal:id/tvAirportName");

                MobileElement airportLocationName = searchedAirportList
                        .get(flightIndex)
                        .findElementById("com.tajawal:id/tvAirportLocationName");

                if (airportName.getText().equalsIgnoreCase("All Airports")) {
                    // AS this selection does not have code.
                    // so "com.tajawal:id/tvAirportCode" causes Delay.
                    continue;
                }

                // Matching flights with the Flight Code (i.e DXB, AUH etc)
                MobileElement element = searchedAirportList
                        .get(flightIndex)
                        .findElement(new By.ById("com.tajawal:id/tvAirportCode"));

                if (element != null && element.getText().equalsIgnoreCase(airportCode)) {

                    element.click();
                    logger.info("Successfully Selected flight:" + element.getText()
                            + " of Fight Code:" + airportCode);
                    break;
                }

            }
            catch(Exception e) {
                logger.info("Search flight Element not found. " +
                        "so move to next element in array to find.");
            }

        }
    }

    // Enter search code in search bar and select origin airport
    public void searchAndSelectOriginAirport() {
        int index = generateRandomNumber(originAirportList.length);
        String selectedOriginAirport = originAirportList[index];
        searchedOriginFlightCode = selectedOriginAirport;
        typeAirportCode(selectedOriginAirport);
        waitForAirportResults();
        selectAirportByCode(selectedOriginAirport);
    }

    // Enter search code in search bar and select destination airport
    public void searchAndSelectDestinationAirport() {
        int index = generateRandomNumber(destinationAirportList.length);
        String selectedDestinationAirport = destinationAirportList[index];
        searchedDestinationFlightCode = selectedDestinationAirport;
        typeAirportCode(selectedDestinationAirport);
        waitForAirportResults();
        selectAirportByCode(selectedDestinationAirport);
    }
}
