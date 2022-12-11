package step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.BrowserUtils;
import utils.ConfigReader;
import utils.CucumberLogUtils;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        // initialize the drive object to open the browser
        BrowserUtils.getDriver();
        CucumberLogUtils.initScenario(scenario);
    }

    @After
    public void tearDown(Scenario scenario) {
        CucumberLogUtils.logInfo("Tes Result -> " +!scenario.isFailed(), false);
        CucumberLogUtils.logInfo("Attachment", Boolean.parseBoolean(ConfigReader.readProperty("takeScreenshotAfterMethod")));
        BrowserUtils.quitDriver();
    }
}
