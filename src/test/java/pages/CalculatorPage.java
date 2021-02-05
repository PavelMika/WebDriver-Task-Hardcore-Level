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
    private By switchToCalculator = By.xpath("//b[text()='Google Cloud Platform Pricing Calculator']/parent::a");
    private By newFirstFrame = By.xpath("//iframe[contains(@name,'goog_')]");
    private By instancesField =
            By.xpath("//md-input-container/child::input[@ng-model='listingCtrl.computeServer.quantity']");
    private By seriesOfMachine = By.xpath("//md-select[@name='series']/parent::md-input-container");
    private By seriesOfMachineModel = By.xpath("//md-option[@value='n1']");
    private By machineType = By.xpath("//label[text()='Machine type']/parent::md-input-container");
    private By computeEngine = By.xpath("//md-option[@value='CP-COMPUTEENGINE-VMIMAGE-N1-STANDARD-8']");
    private By gpusCheckBox = By.xpath("//md-checkbox[@aria-label='Add GPUs']");
    private By numberOfGpus = By.xpath("//md-select[@placeholder='Number of GPUs']");
    private By numberOfGpusModel = By.cssSelector("md-option[value='1'][class='ng-scope md-ink-ripple'][ng-disabled]");
    private By gpuType = By.xpath("//md-select[@placeholder='GPU type']");
    private By gpuTypeModel = By.xpath("//md-option[@value='NVIDIA_TESLA_V100']");
    private By localSsd = By.xpath("//md-select[@placeholder='Local SSD']");
    private By localSsdModel = By.cssSelector("md-option[value='2'][ng-repeat*='supportedSsd']");
    private By dataCenterLocation = By.xpath("//md-select[@placeholder='Datacenter location']");
    private By dataCenterLocationInFrankfurt = By.cssSelector("md-select-menu[class='md-overflow']" +
            " md-option[value='europe-west3'][ng-repeat*='fullRegionList']");
    private By committedUsage = By.xpath("//md-select[@placeholder='Committed usage']");
    private By oneYearUsage = By.cssSelector("div[class='md-select-menu-container md-active md-clickable']" +
            " md-option[value='1'][class='md-ink-ripple']");
    private By addToEstimateButton = By.xpath("//button[@aria-label='Add to Estimate']");
    private By addressOfMail = By.xpath("//div[@id='copy_address']");
    private By costFromCalculator = By.cssSelector("div>b[class=ng-binding]");
    private By emailEstimate = By.id("email_quote");
    private By email = By.cssSelector("input[name = description][type=email]");
    private By emailSendingButton = By.cssSelector("button[type=button][aria-label='Send Email']");
    private By buttonMailPushing = By.xpath("//div[@class='mail_message']");
    private By costFromMail = By.xpath("//td/h3[contains(text(),'USD')]");


    public void openCloudPage(){
        driver.get("https://cloud.google.com/");
    }

    public void goToGoogleCloudPlatformPricingCalculatorPage(String keyForCalculatorPageOpening) {
        driver.findElement(devSiteSearch).click();
        WebElement textForGoogleSearch = driver.findElement(googleSearch);
        textForGoogleSearch.click();
        textForGoogleSearch.sendKeys(keyForCalculatorPageOpening);
        textForGoogleSearch.sendKeys(Keys.ENTER);
        driver.findElement(switchToCalculator).click();
    }

    public void sendKeyInToNumberOfInstancesField(String keyForNumberOfInstances) {
        WebElement element = driver.findElement(newFirstFrame);
        driver.switchTo().frame(element);
        driver.switchTo().frame("myFrame");
        WebElement numberOfInstancesField = driver.findElement(instancesField);
        numberOfInstancesField.sendKeys(keyForNumberOfInstances);
    }

    public void selectSeriesOfMachine() {
        driver.findElement(seriesOfMachine).click();
        driver.findElement(seriesOfMachineModel).click();
    }

    public void selectMachineType() {
        driver.findElement(machineType).click();
        driver.findElement(computeEngine).click();
    }

    public void clickAddGpusCheckBox() {
        driver.findElement(gpusCheckBox).click();
    }

    public void selectNumberOfGpus() {
        driver.findElement(numberOfGpus).click();
        driver.findElement(numberOfGpusModel).click();
    }

    public void selectGpuType() {
        driver.findElement(gpuType).click();
        driver.findElement(gpuTypeModel).click();
    }

    public void selectLocalSsd() {
        driver.findElement(localSsd).click();
        driver.findElement(localSsdModel).click();
    }

    public void selectDataCenterLocation() {
        driver.findElement(dataCenterLocation).click();
        driver.findElement(dataCenterLocationInFrankfurt).click();
    }

    public void selectCommittedUsage() {
        driver.findElement(committedUsage).click();
        driver.findElement(oneYearUsage).click();
    }

    public void pushAddToEstimate() {
        driver.findElement(addToEstimateButton).click();
    }

    public void checkTheMessageWithThePrice() {
        String calculatorWindow = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://10minutemail.com/");
        String mailWindow = driver.getWindowHandle();
        driver.findElement(addressOfMail).click();//
        driver.switchTo().window(calculatorWindow);
        WebElement element = driver.findElement(newFirstFrame);
        driver.switchTo().frame(element);
        driver.switchTo().frame("myFrame");
        String textFromCalculator = driver.findElement(costFromCalculator).getText();
        driver.findElement(emailEstimate).click();
        driver.findElement(email).click();
        Actions actionProvider = new Actions(driver);
        Action keyDown = actionProvider.keyDown(Keys.CONTROL).sendKeys("v").build();
        ((Action) keyDown).perform();
        driver.findElement(emailSendingButton).click();
        driver.switchTo().window(mailWindow);
        WebElement loadOfButtonMail = (new WebDriverWait(driver, 10)).
                until(ExpectedConditions.presenceOfElementLocated(buttonMailPushing));
        loadOfButtonMail.click();
        String textFromMail = driver.findElement(costFromMail).getText();
        Boolean overlap;
        if (textFromCalculator.contains(textFromMail)) {
            overlap = true;
        } else {
            overlap = false;
        }
        assertTrue(overlap);
    }

}
