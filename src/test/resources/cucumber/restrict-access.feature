Feature: Restrict access to logged in users only

Scenario: Not logged in user tries to access create proposal page
  Given user is on the Login page
  When user inputs in username
  And user manually types in the create proposal page
  Then user should be redirected to login page

Scenario: Not logged in user tries to access event search page
  Given user is on the Login page
  When user inputs in username
  And user manually types in the event search page
  Then user should be redirected to login page

Scenario: Not logged in user tries to access proposal details page
  Given user is on the Login page
  When user inputs in username
  And user manually types in the proposal details page
  Then user should be redirected to login page