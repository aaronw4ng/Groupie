Feature: User search filter when searching for users to invite
  Scenario: User tries to invite friend
    Given user is on the Login page
    When user inputs in username
    And user inputs "password1" in password
    And user clicks "btn-login" button
    And user accepts the alert
    And user clicks "btn-add-users" button
    And user types in friend
    Then user results should be filtered