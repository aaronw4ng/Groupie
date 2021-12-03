Feature: Responde to received proposals with availability and excitement
Scenario: User1 sends proposal to user2, user2 responds with yes available and excitement to one event
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
    And user clicks first proposal in received proposals
    And user clicks first event on proposal page
    And user selects available and excitement of 2
    And user clicks save response button
    Then user gets response saved message

Scenario: User1 sends proposal to user2, user2 responds with maybe available and excitement to one event
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
    And user clicks first proposal in received proposals
    And user clicks first event on proposal page
    And user selects maybe and excitement of 3
    And user clicks save response button
    Then user gets response saved message

Scenario: User1 sends proposal to user2, user2 responds with not available to one event
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
    And user clicks first proposal in received proposals
    And user clicks first event on proposal page
    And user selects not available
    And user clicks save response button
    Then user gets response saved message

Scenario: User1 sends proposal to user2, user2 responds with not available to one event, but later changes to available with excitement of 5
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
    And user clicks first proposal in received proposals
    And user clicks first event on proposal page
    And user selects not available
    And user clicks save response button
    And user accepts the alert
    And user navigates to View Proposals page
    And user clicks first proposal in received proposals
    And user clicks first event on proposal page
    And user selects available and excitement of 5
    And user clicks save response button
    Then user gets response saved message