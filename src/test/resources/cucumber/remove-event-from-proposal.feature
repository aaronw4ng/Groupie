Feature: Owner can remove event from proposal at anytime

Scenario: Owner removes an event from draft proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove An Event From Draft Proposal" in proposal name
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
  And user navigates to View Proposals page
  And user clicks on "Remove An Event From Draft Proposal" proposal
  And user presses button to remove the "Eric Nam" event
  And user accepts event delete alert
  Then user should see an alert message "Successfully deleted event"

Scenario: Owner removes the last event from draft proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user inputs "Remove Last Event From Draft Proposal" in proposal name
  And user clicks "btn-add-users" button
  And user adds first user result
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  And user adds first event result
  And user clicks "btn-save-draft" button
  And user navigates to View Proposals page
  And user clicks on "Remove An Event From Draft Proposal" proposal
  And user presses button to remove the "Eric Nam" event
  And user accepts event delete alert
  And user accepts deleting event will delete proposal alert
  Then user should see an alert message "Successfully deleted event"

Scenario: Owner removes an event from sent proposal
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
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
  And user navigates to View Proposals page
  And user clicks on "Remove An Event From Sent Proposal" proposal
  And user presses button to remove the "Eric Nam" event
  And user accepts event delete alert
  Then user should see an alert message "Successfully deleted event"

Scenario: Owner removes the last event from sent proposal
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
  And user clicks on "Remove An Event From Sent Proposal" proposal
  And user presses button to remove the "Eric Nam" event
  And user accepts event delete alert
  And user accepts deleting event will delete proposal alert
  Then user should see an alert message "Successfully deleted event"