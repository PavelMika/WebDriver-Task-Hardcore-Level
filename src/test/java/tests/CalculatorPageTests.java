package tests;

import org.testng.annotations.Test;

public class CalculatorPageTests extends BaseTest{

    @Test
    public void checkTheValidityOfTheSentMessageWithThePrice(){
        calculatorPage.openCloudPage();
        calculatorPage.goToGoogleCloudPlatformPricingCalculatorPage(
                "Google Cloud Platform Pricing Calculator");
        calculatorPage.sendKeyInToNumberOfInstancesField("4");
        calculatorPage.selectSeriesOfMachine();
        calculatorPage.selectMachineType();
        calculatorPage.clickAddGpusCheckBox();
        calculatorPage.selectNumberOfGpus();
        calculatorPage.selectGpuType();
        calculatorPage.selectLocalSsd();
        calculatorPage.selectDataCenterLocation();
        calculatorPage.selectCommittedUsage();
        calculatorPage.pushAddToEstimate();
        calculatorPage.checkTheMessageWithThePrice();
    }
}
