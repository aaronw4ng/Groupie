Feature: Restrict access to logged in users only

  Scenario: Not logged in user tries to access create proposal page
    Given user is on the Login page
    When user inputs in username
    And user manually types in the create proposal page
    Then user should be redirected to login page