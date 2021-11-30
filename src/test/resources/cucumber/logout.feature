Feature: User logout
  Scenario: Logged in user log outs successfully
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user clicks "logout-btn" button
    Then user should be on Login page

Scenario: User is logged out because of 60s of inactivity
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user does nothing for over 60s
  Then user should be on Login page