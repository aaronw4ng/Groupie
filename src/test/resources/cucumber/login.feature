Feature: User login
Scenario: User logs in with valid credentials
    Given user is on the Login page
    When user inputs "username1" in username
    And user inputs "password1" in password
    Then user should be logged in