package step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import pages.CommonPage;
import pages.HomePage;
import utils.BrowserUtils;

public class HomeSteps implements CommonPage {

    HomePage page;

    public HomeSteps() {
        page = new HomePage();
    }
    @Given("I open url of homepage")
    public void iOpenUrlOfHomepage() {
        BrowserUtils.getDriver();
    }

    @Then("Verify title of the homepage is {string}")
    public void verifyTitleOfTheHomepageIs(String msg) {
        BrowserUtils.assertEquals(BrowserUtils.getDriver().getTitle(), msg);
    }

    @Then("Verify {string} is displayed")
    public void verifyIsDisplayed(String text) {
        WebElement element;

        switch (text) {
            case "10090 Main Street Fairfax, VA, USA":
                element = page.address;
                BrowserUtils.assertTrue(BrowserUtils.isDisplayed(element));
                break;
            case "English":
            case "Spanish":
            case "French":
                element = page.langMenu;
                BrowserUtils.click(element);
                BrowserUtils.assertEquals(getElementByXpath(XPATH_TEMPLATE_LINKTEXT, text).getText(), text);
                break;
            case "https://facebook.com":
            case "https://twitter.com":
            case "https://skype.com":
            case "https://linkedin.com":
                BrowserUtils.assertTrue(getElementByXpath(XPATH_TEMPLATE_FOOTERLINKHREF, text).isDisplayed());
                break;
            default:
                element = getElementByXpath(XPATH_TEMPLATE_TEXT_CONTAINS, text);
                BrowserUtils.assertTrue(BrowserUtils.isDisplayed(element));
                break;
        }
    }

    @Then("Verify {string} is enable")
    public void verifyIsEnable(String navBtn) {
        WebElement element;
        switch (navBtn) {
            case "English":
            case "Spanish":
            case "French":
                element = page.langMenu;
                BrowserUtils.click(element);
                BrowserUtils.assertTrue(getElementByXpath(XPATH_TEMPLATE_LINKTEXT, navBtn).isEnabled());
                break;
            default:
                BrowserUtils.assertTrue(getElementByXpath(XPATH_TEMPLATE_LINKTEXT, navBtn).isEnabled());
        }

    }

    @When("When I click {string}")
    public void whenIClick(String link) {
        BrowserUtils.click(getElementByXpath(XPATH_TEMPLATE_LINKHREF, link));
    }

    @Then("Verify destination of related social media has URL as {string}")
    public void verifyDestinationOfRelatedSocialMediaHasURLAs(String url) {
        BrowserUtils.switchToNewWindow();
        BrowserUtils.assertEquals(BrowserUtils.getDriver().getCurrentUrl(), url);
    }

    @And("Verify {string} navigates to related {string}")
    public void verifyNavigatesToRelated(String button, String url) {
        BrowserUtils.click(getElementByXpath(XPATH_TEMPLATE_FOOTERLINKHREF, button));
        BrowserUtils.switchToNewWindow();
        BrowserUtils.assertEquals(BrowserUtils.getDriver().getCurrentUrl(), url);
    }
}
