Feature: Owner can remove event from proposal at anytime

  Scenario: Owner removes an event from sent proposal
    Given user is on the Create User page
    When two new users are added
    And user inputs "Remove An Event From Sent Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds first user result
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-event-find" button
    And user inputs "Justin Bieber" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-send-proposal" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks on the first proposal
    And user presses button to remove the first event
    Then user sees event delete alert

  Scenario: Owner removes final event from sent proposal
    Given user is on the Create User page
    When two new users are added
    And user inputs "Remove Last Event From Sent Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds first user result
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-send-proposal" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks on the first proposal
    And user presses button to remove the first event
    And user accepts the alert
    Then user sees deleting event will delete proposal alert

  Scenario: Owner removes an event from draft proposal
    Given user is on the Create User page
    When two new users are added
    And user inputs "Remove An Event From Sent Proposal" in proposal name
    And user clicks "btn-add-users" button
    And user adds first user result
    And user clicks "btn-event-find" button
    And user inputs "Eric Nam" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-event-find" button
    And user inputs "Justin Bieber" in event search
    And user clicks "event-search-button" button
    And user adds first event result
    And user clicks "btn-save-draft" button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks on the first proposal
    And user presses button to remove the first event
    Then user sees event delete alert