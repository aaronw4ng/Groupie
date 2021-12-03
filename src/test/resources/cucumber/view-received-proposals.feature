Feature: View received proposals
Scenario: User sends out a first proposal that invites user2, user2 should see it received on View Proposals page
    Given two new users are set up
    And new user one logs in
    And user navigates to Create Proposal page
    And user inputs "My Test Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds newUser2 to the proposal
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-send-proposal" button
    And user accepts the alert
    And user logs out
    And new user two logs in
    And user navigates to View Proposals page
    Then user sees the proposal from first user

Scenario: User sends out a first proposal that invites user2, user2 should see it received in calendar
    Given two new users are set up
    And new user one logs in
    And user navigates to Create Proposal page
    And user inputs "My Test Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds newUser2 to the proposal
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-send-proposal" button
    And user accepts the alert
    And user logs out
    And new user two logs in
    And user navigates to View Proposals page
    Then user sees the proposal from first user on calendar