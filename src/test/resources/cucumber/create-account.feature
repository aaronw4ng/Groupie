Feature: Create Account page
Scenario: Create new user
    Given user is on the Create User page
    When user inputs "username1" in username
    And user inputs "password1" in password
    And user re-types "password1"
    Then new account should be created