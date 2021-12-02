Feature: Set yourself unavailable to others with date option
Scenario: Set yourself unavailable to others indifinetly
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user navigates to Privacy Settings page
    And user types in "-1" in unavailable time box
    And user clicks "unavailable" button
    Then user should see themselve unavailable until indefinitely
    
Scenario:
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user navigates to Privacy Settings page
    And user types in "0.003" in unavailable time box
    And user clicks "unavailable" button
    Then user should see themselve unavailable for 0.002 seconds