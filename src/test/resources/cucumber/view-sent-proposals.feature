Feature: View sent proposals

Scenario: User sends out a first proposal and can see it
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "My Test Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-send-proposal" button
  And user navigates to View Proposals page
  Then user sees their first proposal that they sent out

Scenario: Second user can see proposal that user sent to them
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "My Test Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-send-proposal" button
  And user logs out
  And second user logs in
  And second user navigates to View Proposals page
  Then second user sees the proposal from first user
