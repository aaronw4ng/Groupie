Feature: Create Proposal page

Scenario: User attempts to create proposal with empty events and users selections
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user clicks "btn-send-proposal" button
    Then user should see an error alert