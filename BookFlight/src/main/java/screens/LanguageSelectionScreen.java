package screens;

import base.ScreenBase;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
* This class select language i.e English, Arabic
*/

public class LanguageSelectionScreen extends ScreenBase {

    @FindBy(id="com.tajawal:id/welcomeEnglishButton")
    public MobileElement englishLanguageButton;

    @FindBy(id="com.tajawal:id/ctaText")
    public MobileElement continueButton;

    public LanguageSelectionScreen(MobileDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public LanguageSelectionScreen clickEnglishButton() {
        englishLanguageButton.click();
        return this;
    }

    public LanguageSelectionScreen clickContinueButton() {
        continueButton.click();
        return this;
    }

    public String getEnglishButtonText() {
        return englishLanguageButton.getText();
    }

    public String getContinueButtonText() {
        return continueButton.getText();
    }


}
