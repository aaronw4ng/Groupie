Feature: Owner can delete a proposal at any time

Scenario: User deletes all users in proposal details
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks the first owned proposal
    And user deletes all users 
    And user accepts the confirmation
    Then the proposal should be deleted

Scenario: User deletes all events in proposal details
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks the first owned proposal
    And user deletes all events
    And user accepts the confirmation
    Then the proposal should be deleted

Scenario: User deletes proposal in proposal details
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks the first owned proposal 
    And user clicks the delete proposal button
    And user accepts the confirmation 
    Then the proposal should be deleted 

