Feature: View sent proposals

Scenario: User sends out a first proposal that invites user2, user1 should see it sent out on View Proposals page
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
  And user navigates to View Proposals page
  Then user sees their first proposal that they sent out

Scenario: User sends out a first proposal that invites user2, user1 should see it sent out on the calendar
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
  And user navigates to View Proposals page
  Then user sees their first proposal that they sent out on the calendar