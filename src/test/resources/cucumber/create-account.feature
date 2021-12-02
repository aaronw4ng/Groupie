Feature: Create Account page
Scenario: Create new user
    Given user is on the Create User page
    When user inputs in username
    And user inputs "password1" in password
    And user re-types "password1"
    And user clicks "btn-create-account" button
    Then new account should be created

Scenario: Create second new user
    Given user is on the Create User page
    When user inputs in second username
    And user inputs "password1" in password
    And user re-types "password1"
    And user clicks "btn-create-account" button
    Then new account should be created

Scenario: Create third new user
    Given user is on the Create User page
    When user inputs in third username
    And user inputs "password1" in password
    And user re-types "password1"
    And user clicks "btn-create-account" button
    Then new account should be created

Scenario: User goes back to Login
    Given user is on the Create User page
    When user clicks "btn-back" button
    Then user should be on Login page

Scenario: Attempted account creation with empty fields
    Given user is on the Create User page
    When user clicks "btn-create-account" button
    Then user should be shown "empty-username,empty-password,empty-confirm-password" error message

Scenario: Attempted account creation with taken username
    Given user is on the Create User page
    When user inputs in username
    And user inputs "password1" in password
    And user re-types "password1"
    And user clicks "btn-create-account" button
    Then user should be shown "username-taken" error message

Scenario: Attempted account creation with empty password confirmation field
    Given user is on the Create User page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-create-account" button
    Then user should be shown "empty-confirm-password" error message

Scenario: Attempted account creation with mismatched password fields
    Given user is on the Create User page
    When user inputs in username
    And user inputs "password1" in password
    And user re-types "password2"
    And user clicks "btn-create-account" button
    Then user should be shown "password-match" error message

Scenario: Attempted account creation with invalid password
    Given user is on the Create User page
    When user inputs in username
    And user inputs "a" in password
    And user re-types "a"
    And user clicks "btn-create-account" button
    Then user should be shown "password-length" error message

