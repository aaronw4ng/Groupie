Feature: Owner can remove invitee from proposal at anytime

Scenario: Owner removes an invitee from draft proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove An Invitee From Draft Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user adds second user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-save-draft" button
  And user navigates to View Proposals page
  And user clicks on "Remove An Invitee From Draft Proposal" proposal
  And user presses button to remove the first invitee
  And user accepts remove invitee alert
  Then user should see an alert message "Successfully removed invitee"

Scenario: Owner removes the last invitee from draft proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove Last Invitee From Draft Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-save-draft" button
  And user navigates to View Proposals page
  And user clicks on "Remove Last Invitee From Draft Proposal" proposal
  And user presses button to remove the first invitee
  And user accepts remove invitee alert
  And user accepts removing invitee will delete proposal alert
  Then user should see an alert message "Successfully removed invitee"

Scenario: Owner removes an invitee from sent proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove An Invitee From Sent Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user adds second user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-send-proposal" button
  And user navigates to View Proposals page
  And user clicks on "Remove An Invitee From Sent Proposal" proposal
  And user presses button to remove the first invitee
  And user accepts remove invitee alert
  Then user should see an alert message "Successfully removed invitee"

Scenario: Owner removes last invitee from sent proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove Last Event From Sent Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-send-proposal" button
  And user navigates to View Proposals page
  And user clicks on "Remove Last Invitee From Sent Proposal" proposal
  And user presses button to remove the first invitee
  And user accepts remove invitee alert
  And user accepts removing invitee will delete proposal alert
  Then user should see an alert message "Successfully removed invitee"