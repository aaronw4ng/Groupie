Feature: View draft proposals

Scenario: User creates first draft proposal and can see it
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "My First Draft Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-send-proposal" button
  And user navigates to View Proposals page
  Then user sees "My First Draft Proposal" proposal