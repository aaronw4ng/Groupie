Feature: Owner can remove invitee from proposal at anytime

  Scenario: Owner removes an invitee from sent proposal
    Given three new users are set up
    When new user one logs in
    And user inputs "Remove An Invitee From Sent Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds newUser2 to the proposal
    And user adds newUser3 to the proposal
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-send-proposal" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks on the first sent proposal
    And user presses button to remove second user
    Then user sees invitee delete alert

  Scenario: Owner removes an invitee from draft proposal
    Given three new users are set up
    When new user one logs in
    And user inputs "Remove An Invitee From Draft Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds newUser2 to the proposal
    And user adds newUser3 to the proposal
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-save-draft" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks on the first draft proposal
    And user presses button to remove second user from draft
    Then user sees delete invitee from draft alert
