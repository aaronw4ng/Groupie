Feature: User login
Scenario: User logs in with valid credentials
    Given user is on the Login page
    When user inputs "username1" in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    Then user should be logged in

Scenario: User clicks the create account button
    Given user is on the Login page
    When user clicks "btn-create-page" button
    Then user should be on Create Account page