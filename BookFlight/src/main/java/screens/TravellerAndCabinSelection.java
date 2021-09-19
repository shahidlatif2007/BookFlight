package screens;

import base.ScreenBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/*
* This class responsible for selecting number of passengers and cabin class type.
*/

public class TravellerAndCabinSelection extends ScreenBase {

    @FindBys({
        @FindBy(id="com.tajawal:id/paxAdult"),
        @FindBy(id="com.tajawal:id/tvPaxCount")
    })
    private MobileElement adultPassengerCount;

    @FindBys({
        @FindBy(id="com.tajawal:id/paxChild"),
        @FindBy(id="com.tajawal:id/tvPaxCount")
    })
    private MobileElement childPassengerCount;

    @FindBys({
        @FindBy(id="com.tajawal:id/paxInfants"),
        @FindBy(id="com.tajawal:id/tvPaxCount")
    })
    private MobileElement infantPassengerCount;

    @FindBys({
        @FindBy(id="com.tajawal:id/paxChild"),
        @FindBy(id="com.tajawal:id/imgAddPax")
    })
    private MobileElement increaseChildrenPassengerElement;

    @FindBys({
        @FindBy(id="com.tajawal:id/paxInfants"),
        @FindBy(id="com.tajawal:id/imgAddPax")
    })
    private MobileElement increaseInfantPassengerElement;

    @FindBy(id="com.tajawal:id/paxCardView")
    private List<MobileElement> passengerSelection;

    @FindBys({
        @FindBy(id="com.tajawal:id/rvCabinView"),
        @FindBy(id="com.tajawal:id/row_container")
    })
    private List<MobileElement> cabinClassSelection;

    @FindBy(id="com.tajawal:id/paxChild")
    private MobileElement increaseChildrenPassenger;

    @FindBy(id="com.tajawal:id/paxInfants")
    public MobileElement increaseInfantPassenger;

    @FindBy(id="com.tajawal:id/applyMenu")
    private MobileElement applyButton;

    public TravellerAndCabinSelection(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // Increase children Passenger Count
    public TravellerAndCabinSelection increaseChildrenPassengerCount() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, ELEMENT_WAITING_TIMEOUT);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(increaseChildrenPassengerElement));
        increaseChildrenPassengerElement.click();
        return this;
    }

    // Increase infant Passenger Count
    public TravellerAndCabinSelection increaseInfantPassengerCount() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, ELEMENT_WAITING_TIMEOUT);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(increaseChildrenPassengerElement));
        increaseInfantPassengerElement.click();
        return this;
    }

    // Select Cabin Class i.e Economy, Premium Economy etc
    public TravellerAndCabinSelection selectEconomyClass(String classCabin) {

        for (MobileElement element : cabinClassSelection) {
            MobileElement cabinClassName = element.findElementById("com.tajawal:id/tvCabinName");
            MobileElement radioButton = element.findElementById("com.tajawal:id/rdCabinItem");
            String test = cabinClassName.getText();
            if (cabinClassName != null && cabinClassName.getText().equalsIgnoreCase(classCabin)) {
                if (radioButton.isSelected() == false) {
                    cabinClassName.click();
                    break;
                }
            }
        }
        return this;
    }

    public int getAdultPassengerCount() {
        return Integer.parseInt(adultPassengerCount.getText());
    }

    public int getChildrenPassengerCount() {
        return Integer.parseInt(childPassengerCount.getText());
    }

    public int getInfantPassengerCount() {
        return Integer.parseInt(infantPassengerCount.getText());
    }

    public void clickApply() {

        applyButton.click();
    }
}
