Feature: User login
Scenario: User logs in with valid credentials
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    Then user should be logged in

Scenario: User attempts login with empty password field
    Given user is on the Login page
    When user inputs in username
    And user clicks "btn-login" button
    Then user should be shown "empty-password" error message

Scenario: User attempts login with empty username field
    Given user is on the Login page
    And user inputs "password1" in password
    And user clicks "btn-login" button
    Then user should be shown "empty-username" error message

Scenario: User attempts login with all fields empty
    Given user is on the Login page 
    And user clicks "btn-login" button
    Then user should be shown "empty-username,empty-password" error message

Scenario: User clicks the create account button
    Given user is on the Login page
    When user clicks "btn-create-page" button
    Then user should be on Create Account page

Scenario: User login attempts over the limit
    Given user is on the Login page
    When user inputs in username
    And user inputs "meatjuice" in password
    And user clicks "btn-login" button
    And user clicks "btn-login" button
    And user clicks "btn-login" button
    Then the "btn-login" button should be disabled

Scenario: User fails to log in for 4 consecutive times, so have to wait 60 seconds again
    Given user is on the Login page
    When user inputs in username
    And user inputs "meatjuice" in password
    And user clicks "btn-login" button
    And user clicks "btn-login" button
    And user clicks "btn-login" button
    And user waits for login cool down period
    And user clicks "btn-login" button
    Then the "btn-login" button should be disabled
