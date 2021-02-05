package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

import static org.testng.Assert.assertTrue;

public class CalculatorPage extends BasePage {
    public CalculatorPage(WebDriver driver) {
        super(driver);
    }

    private By devSiteSearch = By.className("devsite-search-form");
    private By googleSearch = By.xpath("//input[@class='devsite-search-field devsite-search-query']");
    private By goToCalculator = By.xpath("//b[text()='Google Cloud Platform Pricing Calculator']/parent::a");
    private By goToNewFirstFrame = By.xpath("//iframe[contains(@name,'goog_')]");
    private By pickInstancesField =
            By.xpath("//md-input-container/child::input[@ng-model='listingCtrl.computeServer.quantity']");
    private By pickSeriesOfMachine = By.xpath("//md-select[@name='series']/parent::md-input-container");
    private By clickSeriesOfMachine = By.xpath("//md-option[@value='n1']");
    private By pickMachineType = By.xpath("//label[text()='Machine type']/parent::md-input-container");
    private By pickComputeEngine = By.xpath("//md-option[@value='CP-COMPUTEENGINE-VMIMAGE-N1-STANDARD-8']");
    private By pickAddGpusCheckBox = By.xpath("//md-checkbox[@aria-label='Add GPUs']");
    private  By pickNumberOfGpus = By.xpath("//md-select[@placeholder='Number of GPUs']");
    private By clickNumberOfGpus = By.cssSelector("md-option[value='1'][class='ng-scope md-ink-ripple'][ng-disabled]");
    private By pickGpuType = By.xpath("//md-select[@placeholder='GPU type']");
    private By pickNvidea = By.xpath("//md-option[@value='NVIDIA_TESLA_V100']");
    private By pickLocalSsd = By.xpath("//md-select[@placeholder='Local SSD']");
    private By pickModelLocalSsd = By.cssSelector("md-option[value='2'][ng-repeat*='supportedSsd']");
    private By pickDataCenterLocation = By.xpath("//md-select[@placeholder='Datacenter location']");
    private By pickDataCenterLocationInFrankfurt = By.cssSelector("md-select-menu[class='md-overflow']" +
            " md-option[value='europe-west3'][ng-repeat*='fullRegionList']");
    private By pickCommittedUsage = By.xpath("//md-select[@placeholder='Committed usage']");
    private By pickOneYearUsage = By.cssSelector("div[class='md-select-menu-container md-active md-clickable']" +
            " md-option[value='1'][class='md-ink-ripple']");
    private By pushAddToEstimateButton = By.xpath("//button[@aria-label='Add to Estimate']");
    private By copyAddress = By.xpath("//div[@id='copy_address']");
    private By getTextFromCalculator = By.cssSelector("div>b[class=ng-binding]");
    private By pushEmailEstimate = By.id("email_quote");
    private By inputEmail = By.cssSelector("input[name = description][type=email]");
    private By sendEmail = By.cssSelector("button[type=button][aria-label='Send Email']");
    private By pushButtonMail = By.xpath("//div[@class='mail_message']");
    private By getTextFromMail = By.xpath("//td/h3[contains(text(),'USD')]");


    public void openCloudPage(){
        driver.get("https://cloud.google.com/");
    }

    public void goToGoogleCloudPlatformPricingCalculatorPage(String keyForCalculatorPageOpening) {
        driver.findElement(devSiteSearch).click();
        WebElement textForGoogleSearch = driver.findElement(googleSearch);
        textForGoogleSearch.click();
        textForGoogleSearch.sendKeys(keyForCalculatorPageOpening);
        textForGoogleSearch.sendKeys(Keys.ENTER);
        driver.findElement(goToCalculator).click();
    }

    public void sendKeyInToNumberOfInstancesField(String keyForNumberOfInstances) {
        WebElement element = driver.findElement(goToNewFirstFrame);
        driver.switchTo().frame(element);
        driver.switchTo().frame("myFrame");
        WebElement numberOfInstancesField = driver.findElement(pickInstancesField);
        numberOfInstancesField.sendKeys(keyForNumberOfInstances);
    }

    public void selectSeriesOfMachine() {
        driver.findElement(pickSeriesOfMachine).click();
        driver.findElement(clickSeriesOfMachine).click();
    }

    public void selectMachineType() {
        driver.findElement(pickMachineType).click();
        driver.findElement(pickComputeEngine).click();
    }

    public void clickAddGpusCheckBox() {
        driver.findElement(pickAddGpusCheckBox).click();
    }

    public void selectNumberOfGpus() {
        driver.findElement(pickNumberOfGpus).click();
        driver.findElement(clickNumberOfGpus).click();
    }

    public void selectGpuType() {
        driver.findElement(pickGpuType).click();
        driver.findElement(pickNvidea).click();
    }

    public void selectLocalSsd() {
        driver.findElement(pickLocalSsd).click();
        driver.findElement(pickModelLocalSsd).click();
    }

    public void selectDataCenterLocation() {
        driver.findElement(pickDataCenterLocation).click();
        driver.findElement(pickDataCenterLocationInFrankfurt).click();
    }

    public void selectCommittedUsage() {
        driver.findElement(pickCommittedUsage).click();
        driver.findElement(pickOneYearUsage).click();
    }

    public void pushAddToEstimate() {
        driver.findElement(pushAddToEstimateButton).click();
    }

    public void checkTheMessageWithThePrice() {
        String calculatorWindow = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://10minutemail.com/");
        String mailWindow = driver.getWindowHandle();
        driver.findElement(copyAddress).click();//
        driver.switchTo().window(calculatorWindow);
        WebElement element = driver.findElement(goToNewFirstFrame);
        driver.switchTo().frame(element);
        driver.switchTo().frame("myFrame");
        String textFromCalculator = driver.findElement(getTextFromCalculator).getText();
        driver.findElement(pushEmailEstimate).click();
        driver.findElement(inputEmail).click();
        Actions actionProvider = new Actions(driver);
        Action keyDown = actionProvider.keyDown(Keys.CONTROL).sendKeys("v").build();
        ((Action) keyDown).perform();
        driver.findElement(sendEmail).click();
        driver.switchTo().window(mailWindow);
        WebElement loadOfButtonMail = (new WebDriverWait(driver, 10)).
                until(ExpectedConditions.presenceOfElementLocated(pushButtonMail));
        loadOfButtonMail.click();
        String textFromMail = driver.findElement(getTextFromMail).getText();
        Boolean overlap;
        if (textFromCalculator.contains(textFromMail)) {
            overlap = true;
        } else {
            overlap = false;
        }
        assertTrue(overlap);
    }

}
