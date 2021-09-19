package testCases;

import Utilities.DateFormatter;
import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import screens.*;

public class TajawalBookFlightTest extends TestBase {

    LanguageSelectionScreen languageSelectionScreen;
    TajawalHomeScreen tajawalHomeScreen;
    FlightHomeScreen flightHomeScreen;
    AirportSelectionScreen airportSelectionScreen;
    TravellerAndCabinSelection travellerAndCabinSelection;
    FlightDateSelectionScreen flightDateSelectionScreen;
    FlightsListingScreen flightsListingScreen;

    @BeforeTest
    public void init() {
        super.setUp();
        languageSelectionScreen = new LanguageSelectionScreen(driver);
        tajawalHomeScreen = new TajawalHomeScreen(driver);
        flightHomeScreen = new FlightHomeScreen(driver);
        airportSelectionScreen = new AirportSelectionScreen(driver);
        travellerAndCabinSelection = new TravellerAndCabinSelection(driver);
        flightDateSelectionScreen = new FlightDateSelectionScreen(driver);
        flightsListingScreen = new FlightsListingScreen(driver);

    }

    /*
     * Verify that on language selection screen
     * that continue button text verifies and go to flight home screen.
     */
    @Test(priority = 1 )
    public void selectLanguageAndNavigateToFlightHome() {
        String expectedEnglishButtonText = "English";

        logger.info("Going to Verify that english button title is correct.");
        Assert.assertEquals(expectedEnglishButtonText,
                languageSelectionScreen.getEnglishButtonText(),
                "English button title is not correct.");

        logger.info("Going to click on English language " +
                "button and then click Continue");
        languageSelectionScreen
                .clickEnglishButton()
                .clickContinueButton();

        tajawalHomeScreen.clickBookFlight();
    }


    @Test(priority = 2, dependsOnMethods = "selectLanguageAndNavigateToFlightHome")
    public void bookFlightTest() {

        String roundTripTitle = "ROUND-TRIP";
        logger.info("Selecting Source and Destination airport");
        flightHomeScreen
                .clickRoundTrip(roundTripTitle)
                .clickFlightOrigin();

        logger.info("Searching and Selecting origin airport");
        airportSelectionScreen
                .searchAndSelectOriginAirport();

        logger.info("Searching and Selecting Departure airport");
        airportSelectionScreen
                .searchAndSelectDestinationAirport();

        logger.info("Verifying the origin airport selection");
        Assert.assertTrue(flightHomeScreen.getSelectedOriginAirportName().
                        contains(airportSelectionScreen.originAirportCode()),
                "Selected origin airport is not correct");

        logger.info("Verifying the destination airport selection");
        Assert.assertTrue(flightHomeScreen.getSelectedDestinationAirportName().
                contains(airportSelectionScreen.destinationFlightCode()),
                "Selected destination airport is not correct.");

        flightHomeScreen
                .clickStartDate();

        logger.info("Selecting start date of the the flight");
        flightDateSelectionScreen
                .selectDepartureAndReturnDateInFuture()
                .clickConfirmButton();

        String departureDateString = flightDateSelectionScreen
                .getRandomDepartureDateString();

        String returnDateString = flightDateSelectionScreen
                .getRandomReturnDateString();

        logger.info("Verifying date selection in calendar selection " +
                "and showed in flight input Detail screen.");
        Assert.assertEquals(departureDateString,
                flightHomeScreen.getDepartureDateString(),
                "Selected departure dated in calendar is not same as in " +
                        "Flight Input detail screen");

        Assert.assertEquals(returnDateString, flightHomeScreen.getReturnDateString(),
                "Selected departure dated in calendar is not same as in " +
                "Flight Input detail screen");

        logger.info("Select Travellers with 1 Adult, 2 Children and 1 Infant.");
        flightHomeScreen
                .clickCabinClass();
        int expectedAdultCount = 1;
        int expectedChildrenCount = 2;
        int expectedInfantCount = 1;

        logger.info("Going to increase adult, children and infant count");
        travellerAndCabinSelection
                .increaseChildrenPassengerCount()
                .increaseChildrenPassengerCount()
                .increaseInfantPassengerCount()
                .selectEconomyClass("Economy");

        logger.info("Verifying adult passenger count");
        Assert.assertEquals(expectedAdultCount,
                travellerAndCabinSelection.getAdultPassengerCount(),
                "Adult Passenger Count is not same");

        logger.info("Verifying Children passenger count");
        Assert.assertEquals(expectedChildrenCount,
                travellerAndCabinSelection.getChildrenPassengerCount(),
                "Children Passenger Count is not same");

        logger.info("Verifying Infant passenger count");
        Assert.assertEquals(expectedInfantCount,
                travellerAndCabinSelection.getInfantPassengerCount(),
                "Children Passenger Count is not same");

        travellerAndCabinSelection
                .clickApply();

        logger.info("Class cabin selection done.");



        logger.info("Going to search flights based on set creteria.");
        flightHomeScreen
                .searchFlight();

        logger.info("Now waiting for flight listing.");

        flightsListingScreen = flightsListingScreen
                .waitForFlightListing();

        if (flightsListingScreen == null) {
            logger.info("There are no flights cof current selection cretaria");
            Assert.fail("There are not flight for the selected date range.");
            return;
        }
        flightsListingScreen
                .selectSortByFilterScreen()
        .selectFilter("Lowest Price");

        logger.info("Flight Listing done. " +
                "Now applying lowest flight filter.");

        logger.info("Applying Sort by Lowest and price range filter.");
        FlightFilterScreen flightFilterScreen = flightsListingScreen
                .openFiltersScreen()
                .readMinAndMaxFlightPrices();

        logger.info("Flight button pressed.");
        flightFilterScreen
                .clickBackButton();

        float minFlightPrice = flightsListingScreen
                .findFirstFlightPrice();
        /*
         * Verify that minimum price in filter is same as first flight
         * found in Flight Search Results.
         */
        Assert.assertEquals(flightFilterScreen.getMinFlightPrice(),
                minFlightPrice, "Lowest price sorting is not working");

        /*
         * Verify randomly selected departure and return flight date with flight listing.
         */
        String travellerCountAndDepartureReturnDate = flightsListingScreen
                .getTravellersCountAndDepartureReturnDate();
        logger.info("Traveller count and date in Flight Listing page element is:" +
                travellerCountAndDepartureReturnDate);
        String[]  travellerAndDatesInfo = travellerCountAndDepartureReturnDate.split("-");
        if (travellerAndDatesInfo != null &&
                travellerAndDatesInfo.length == 3) {
            String departureDate = travellerAndDatesInfo[1].trim();
            String returnDate = travellerAndDatesInfo[2].trim();

            String formattedDepartureDate = DateFormatter.dateConversionFromToDestinationFormat(departureDateString,
                    DateFormatter.dateFormatterInFlightInputDetail,"dd MMM");
            String formattedReturnDate = DateFormatter.dateConversionFromToDestinationFormat(returnDateString,
                    DateFormatter.dateFormatterInFlightInputDetail,"dd MMM");
            logger.info("Departure Date from FLight Listing page is:"
                    + departureDate + " randomly selected departure date is:" +  formattedDepartureDate);

            logger.info("Return Date from FLight Listing page is:"
                    + returnDate + " randomly selected departure date is:" +  formattedReturnDate);

            Assert.assertEquals(departureDate,
                    formattedDepartureDate,
                    "Selected random departure date is not same as in Flight Listing Page");

            Assert.assertEquals(returnDate,
                    formattedReturnDate,
                    "Selected random return date is not same as in Flight Listing Page");
        }

        /*
         * Verify that first and last flight price is same.
         */
        float maximumFlightPrice = flightsListingScreen.getLastFlight();
        if (flightsListingScreen.isExceptionOccurerdDuringScrolling() == false) {
            Assert.assertEquals(maximumFlightPrice,
                    flightFilterScreen.getMaxFlightPrice(), "Last flight price is not same as in filter screen");
        }else {
            Assert.fail("Cannot verify the last flight  price " +
                    "because exception occur during scrolling");
        }
    }

    @AfterTest
    public void quitDriver() {
        driver.quit();
    }
}
