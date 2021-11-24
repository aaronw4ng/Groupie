Feature: Search Events

Scenario: User searches based on empty fields
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user clicks "event-search-button" button
  Then user should see "No events found."

Scenario: User searches based on keyword
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user inputs "Eric Nam" in event search
  And user clicks "event-search-button" button
  Then user should see "Eric Nam" event name

Scenario: User searches based on start date
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user selects "01-01-2022" in start date
  And user clicks "event-search-button" button
  Then user should see events after "01-01-2022"

Scenario: User searches based on end date
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user selects "01-01-2022" in end date
  And user clicks "event-search-button" button
  Then user should see events before "01-01-2022"

Scenario: User searches based on city
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user inputs "Sacramento" in city
  And user clicks "event-search-button" button
  Then user should see events located in city "Sacramento"

Scenario: User searches based on zipcode
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user inputs "90007" in zipcode
  And user clicks "event-search-button" button
  Then user should see events located in zipcode "90007"

Scenario: User searches based on genre
  Given user is on the Login page
  When user inputs in username
  And user inputs "password1" in password
  And user clicks "btn-login" button
  And user accepts the alert
  And user clicks "btn-event-find" button
  And user selects "Pop" in genre
  And user clicks "event-search-button" button
  Then user should see events related to "Pop"