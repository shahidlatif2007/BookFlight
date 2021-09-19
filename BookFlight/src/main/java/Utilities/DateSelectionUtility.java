package Utilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;


import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* This class is responsible for selected
* date from calendar component by scrolling on different dates.
* First I randomly selected month and year.
* First it creates array of month and then randomly selecting month and year.
* It also created random departure and return date.
 */

public class DateSelectionUtility {

    private MobileDriver driver;
    private MobileElement calendarScrollElement;

    private int numberOfRetries = 30;

    // These variable will contain Start and end date of flight selection.
    private LocalDate firstDayOfTheDate = null;
    private LocalDate lastDayOfTheDate = null;

    private String monthViewElementClassName = "android.widget.TextView";
    private String calendarDatesClassName = "b.a.a.g.t.j.a";
    private String scrollToMonthWithText = "";
    private ScrollUtility scrollUtility;

    public String randomDepatureDateString;
    public String randomReturnDateString;

    public static Logger logger = Logger.getLogger(DateSelectionUtility.class);

    private List<MobileElement> monthView() {
        return calendarScrollElement.findElementsByClassName(monthViewElementClassName);
    }

    private List<MobileElement> calendarDatesViews() {
        return calendarScrollElement.findElementsByClassName(calendarDatesClassName);
    }
    private int calendarViewHeight() {
        int outputHeight = 0;
        List<MobileElement> monthViewElements = monthView();
        for(MobileElement month: monthViewElements) {
            outputHeight = month.getSize().height;
            break;
        }

        List<MobileElement> calendarDatesElements = calendarDatesViews();
        for (MobileElement element: calendarDatesElements) {
            outputHeight += element.getSize().height;
            break;
        }
        return outputHeight;
    }

    private int generateRandomNumber(int arrayLength) {
        Random random = new Random();
        return random.nextInt(arrayLength);
    }

    // The following function will generate two number i.e (1,30), (2, 40)
    private int generateRandomNumberBetweenNumbers(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high-low) + low;
        return result;
    }

    public void selectDates() {

        Boolean isScrollToElement = false;
        while(numberOfRetries > 0) {
            MobileElement monthElement = monthView().get(0);
            if (monthElement.getText().equalsIgnoreCase(scrollToMonthWithText)) {
                logger.info("Date found: Random Month:"
                        + scrollToMonthWithText
                        + " and Current Month On Calender:" + monthElement.getText());
                isScrollToElement = true;
                break;
            }else {
                logger.info("Date Not found: Random Month:"
                        + scrollToMonthWithText +
                        " and Current Month On Calender:" + monthElement.getText());
                int outputHeight = calendarViewHeight();
                scrollUtility.swipeDown(outputHeight);
            }

            numberOfRetries--;
        }

        if (isScrollToElement) {
            List<MobileElement> calendarDateElements = calendarDatesViews();
            MobileElement elements = calendarDateElements.get(0);
            List<MobileElement> dates = elements
                    .findElementsByClassName("android.widget.CheckedTextView");

            int startDate = generateRandomNumberBetweenNumbers(1, 15);
            int endDate = generateRandomNumberBetweenNumbers(startDate + 2, startDate + 5);
            logger.info("Departure Flight Day: "
                    + startDate + " Return Flight Day: " + endDate);
            for (MobileElement date: dates) {
                if (date.getText().equalsIgnoreCase(startDate + "")) {
                    randomDepatureDateString = String.format("%02d",startDate) + " " + scrollToMonthWithText;
                    date.click();
                } else if (date.getText().equalsIgnoreCase(endDate + "")) {
                    randomReturnDateString = String.format("%02d",endDate)  + " " + scrollToMonthWithText;
                    date.click();
                }
            }
        }
    }

    private List<LocalDate> createRandomMonths() {
        List<LocalDate> output = new ArrayList<LocalDate>();
        for (int index = 1; index <= 4; index++) {
            output.add(LocalDate.now().plusMonths(index));
        }
        return output;
    }

    private void randomDatesAndMonthCreation() {
        List<LocalDate> months = createRandomMonths();
        int index = generateRandomNumber(months.size());
        LocalDate date = months.get(index);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        scrollToMonthWithText = formatter.format(date);

        firstDayOfTheDate = date.with(TemporalAdjusters.firstDayOfMonth());
        lastDayOfTheDate = date.with(TemporalAdjusters.lastDayOfMonth());

    }

    public DateSelectionUtility(MobileDriver driver, MobileElement calendarScrollElement) {
        this.driver = driver;
        this.calendarScrollElement = calendarScrollElement;
        randomDatesAndMonthCreation();
        scrollUtility = new ScrollUtility(driver, "com.tajawal:id/calendarView");
    }
}
