package com.vrgsl.step;

import com.thoughtworks.gauge.Step;
import com.virgosol.qa.web.core.di.InjectionHelper;
import com.virgosol.qa.web.core.element.Element;
import com.virgosol.qa.web.core.helper.ConfigurationHelper;
import com.virgosol.qa.web.core.helper.ElementHelper;
import com.virgosol.qa.web.core.helper.LanguageHelper;
import com.virgosol.qa.web.core.helper.StoreHelper;
import com.virgosol.qa.web.core.wait.WaitingAction;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class StepImpl {

    private final Logger logger = LoggerFactory.getLogger(StepImpl.class);

    @Inject
    Element element;

    @Inject
    WebDriver driver;

    @Inject
    WaitingAction waitingAction;

    ElementHelper elementHelper;
    StoreHelper storeHelper;
    LanguageHelper languageHelper;
    //test comment
    private String lang;

    public StepImpl() {
        InjectionHelper.getInstance().getFeather().injectFields(this);
        elementHelper = ElementHelper.getInstance();
        storeHelper = StoreHelper.INSTANCE;
        languageHelper = LanguageHelper.INSTANCE;
        lang = ConfigurationHelper.INSTANCE.getConfiguration().getLanguage();
    }
    public void scrollDown() {
        try {
            int i;
            for(i = 0; i <= 30; ++i) {
                ((JavascriptExecutor)this.driver).executeScript("window.scrollBy(0," + i + ")", new Object[]{""});
            }
            while(i > 0) {
                ((JavascriptExecutor)this.driver).executeScript("window.scrollBy(0," + i + ")", new Object[]{""});
                --i;
            }
        } catch (WebDriverException var2) {
        } catch (Exception var3) {
        }

    }
    public void scrollUp() {
        try {
            int i;
            for(i = 0; i <= 30; ++i) {
                ((JavascriptExecutor)this.driver).executeScript("window.scrollBy(0,-" + i + ")", new Object[]{""});
            }
            while(i > 0) {
                ((JavascriptExecutor)this.driver).executeScript("window.scrollBy(0,-" + i + ")", new Object[]{""});
                --i;
            }
        } catch (WebDriverException var2) {
        } catch (Exception var3) {
        }
    }



    @Step({"Dil Seçilir", "Select Language"})
    public void selectLanguage() {
        element.findByPresenceKey("languageDropdown").hover();
        waitingAction.waitUntil(ExpectedConditions.presenceOfAllElementsLocatedBy(elementHelper.getElementInfoToBy("languageOptions")));
        List<WebElement> languageOptions = driver.findElements(elementHelper.getElementInfoToBy("languageOptions"));
        if (lang.equals("en")) {
            element.findByEqualsAnyText(languageOptions, "EN").click();
        } else if (lang.equals("tr")) {
            element.findByEqualsAnyText(languageOptions, "TR").click();
        }
    }

    @Step({"Sayfadaki <key> butonuna tıkla", "Click on page button <key>"})
    public void clickOnPageButton(String key) {
        try {
            element.findByPresenceKey(key).click();
        } catch (Exception e) {
            fail("Element could not be clicked: " + e.getMessage());
        }
    }

    @Step({"Ekranda <x> <y> koordinatına tıkla", "Tap coordinate on screen <x> <y>"})
    public void tapCoordinate(String x, String y) {
        int xx = Integer.parseInt(x);
        int yy = Integer.parseInt(y);
        Actions actions = new Actions(driver);
        actions.moveByOffset(xx, yy).click().perform();
    }

    @Step({"Sayfayı <key> kez aşağı kaydır", "Scroll down the page <key> times"})
    public void swipeToDownMultipleTimes(int key) {
        for (int i = 0; i < key; i++) {
            this.scrollDown();
        }
    }

    @Step({"Sayfayı <key> kez yukarı kaydır", "Scroll up the page <key> times"})
    public void swipeToUpMultipleTimes(int key) {
        for (int i = 0; i < key; i++) {
            this.scrollUp();
        }
    }

    @Step({"Ekranı <count> kez <pressTime> ms ile aşağı kaydır", "Swipe down <count> with <pressTime> ms"})
    public void swipeDownWithPressTimePage(String count, String pressTime) {
        for (int i = 0; i < Integer.parseInt(count); ++i) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, window.innerHeight);");
            try {
                Thread.sleep(Long.parseLong(pressTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void fail(String message){
        Assert.fail(message);
    }

}
