package utils;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BrowserUtils {

    //private constructor to implement Singleton Design Class
    private final static boolean TAKESCREENSHOT = Boolean.parseBoolean(ConfigReader.readProperty("takeScreenshot"));
    private static WebDriver driver;

    public static WebDriver getDriver(){
        if (driver == null)
            initializeDriver("chrome");
        return driver;
    }

    public static void closeDriver(){
        if (driver != null){
            driver.close();
            driver = null;
        }
    }

    public static void quitDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }

    private static void initializeDriver(String browser){
        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name");
        }



        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigReader.readProperty("url"));

    }

    public static void waitForElementClickability(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementVisibility(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForTxtToBePresent(WebElement element, String txt){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.textToBePresentInElement(element, txt));
    }
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void moveIntoView(WebElement element){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 2; i++) {
            try {
                if (i % 2 == 0) {
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black;" +
                            "border: 3px solid red; background: yellow");
                    //TODO:apply report screenshot here
                    CucumberLogUtils.logInfo(splitElement(element), TAKESCREENSHOT);
                } else {
                    sleep(600);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendKeys(WebElement element, String inputText){
        //TODO: apply report -> logInfo("Entered the text ", element);
        CucumberLogUtils.logInfo("Entered the text: " + inputText + ", Element: " + splitElement(element), TAKESCREENSHOT );
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        element.sendKeys(inputText);
    }

    public static String getText(WebElement element){
        //TODO: apply report -> logInfo("Retrieved the text ", element);
        CucumberLogUtils.logInfo("Retrieved the text: " + splitElement(element), TAKESCREENSHOT);
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        return element.getText();
    }

    public static void click(WebElement element){
        //TODO: apply report -> logInfo("clicked the button ", element);
        CucumberLogUtils.logInfo("Clicked the button" + splitElement(element), TAKESCREENSHOT);
        waitForElementClickability(element);
        moveIntoView(element);
        highlightElement(element);
        element.click();
    }

    public static void waitScrollAndClick(WebElement element){
        //TODO: apply report -> logInfo("clicked the button ", element);
        CucumberLogUtils.logInfo("Clicked the button" + splitElement(element), TAKESCREENSHOT);
        waitForElementClickability(element);
        moveIntoView(element);
        highlightElement(element);
        element.click();
    }

    public static void assertEquals(String actual, String expected){
        //TODO: apply report -> logInfo("Expected: " + expected);
        //TODO: apply report -> logInfo("Actual: " + actual);
        CucumberLogUtils.logInfo("Actual: " + actual + " | Expected: " + expected, TAKESCREENSHOT);
        Assert.assertEquals(expected, actual);
    }

    public static void assertFalse(boolean result){
        //TODO: apply report -> logInfo("Expected: " + result);
        CucumberLogUtils.logInfo("Assert False: " + result, TAKESCREENSHOT);
        Assert.assertFalse(result);
    }

    public static void assertTrue(boolean result){
        //TODO: apply report -> logInfo("Expected: " + result);
        CucumberLogUtils.logInfo("Assert True: " + result, TAKESCREENSHOT);
        Assert.assertTrue(result);
    }

    public static boolean isDisplayed(WebElement element){
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        return element.isDisplayed();
    }

    public static boolean isEnabled(WebElement element){
        waitForElementClickability(element);
        moveIntoView(element);
        highlightElement(element);
        return element.isEnabled();
    }

    public static boolean isDisabled(WebElement element){
        moveIntoView(element);
        highlightElement(element);

        if(element.isEnabled()){
            return false;
        }else {
            return true;
        }
    }

    public static void switchToNewWindow(){
        for(String each: driver.getWindowHandles()){
            if (!each.equals(driver.getWindowHandle()))
                driver.switchTo().window(each);
        }
    }

    public static void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    private static String splitElement(WebElement element) {
        String result = element.toString().split(" -> ")[1];
        return result.substring(0, result.length() - 1);
    }
}