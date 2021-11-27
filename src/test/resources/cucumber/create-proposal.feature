Feature: Create Proposal page

Scenario: User successfully creates a proposal
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user inputs "My Test Proposal" in proposal name
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-add-users" button
    And user inputs "username1" in user search
    And user adds first user result
    And user clicks "btn-send-proposal" button
    Then user should have successfully sent proposal

Scenario: User successfully saves a draft
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user inputs "My Test Proposal" in proposal name
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-add-users" button
    And user inputs "username1" in user search
    And user adds first user result
    And user clicks "btn-save-draft" button
    Then user should have successfully sent proposal

Scenario: User attempts to create a proposal with empty events
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user inputs "No Events" in proposal name
    And user clicks "btn-add-users" button
    And user inputs "username1" in user search
    And user adds first user result
    And user clicks "btn-send-proposal" button
    Then user should have successfully sent proposal

Scenario: User attempts to create proposal with empty events and users selections
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user inputs "No Events and No Users" in proposal name
    And user clicks "btn-send-proposal" button
    Then user should see an error alert