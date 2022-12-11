Feature: Home Page Tests
  Background: Open url of homepage

  @ASB-3 @regression
  Scenario: Verify address and phone number are displayed under the main navigation bar on top of the page
    Then Verify "10090 Main Street Fairfax, VA, USA" is displayed
    Then Verify "703-831-3217" is displayed

  @ASB-6 @regression
  Scenario: Test title of homepage
    Then Verify title of the homepage is "Advance Systems - Home"

  @ASB-7 @regression
  Scenario Outline: Verify navigation bar on the top right is displayed and enable
    Then Verify "<topNavBtn>" is displayed
    And Verify "<topNavBtn>" is enable
    Examples:
      | topNavBtn   |
      | Get Support |
      | Job Career  |
      | Feedback    |
      | English     |
      | Spanish     |
      | French      |



  @ASB-10-B @regression @smoke
  Scenario Outline: Main social media section destinations
    When When I click "<icon>"
    Then Verify destination of related social media has URL as "<URL>"
    Examples:
      | icon                 | URL                        |
      | https://facebook.com | https://www.facebook.com/  |
      | https://twitter.com  | https://twitter.com/       |
      | https://google.com   | https://www.google.com/    |
      | https://linkedin.com | https://www.linkedin.com/  |

  @ASB-16 @regression
  Scenario Outline:  Social media buttons displayed in the footer section
    Then Verify "<socialMediaBtn>" is displayed
    And Verify "<socialMediaBtn>" navigates to related "<URL>"
    Examples:
      | socialMediaBtn       | URL                         |
      | https://facebook.com | https://www.facebook.com/   |
      | https://twitter.com  | https://twitter.com/        |
      | https://skype.com    | https://www.skype.com/en/   |
      | https://linkedin.com | https://www.linkedin.com/   |
